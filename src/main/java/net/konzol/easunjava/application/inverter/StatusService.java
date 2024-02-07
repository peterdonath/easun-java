package net.konzol.easunjava.application.inverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.konzol.easunjava.application.metrics.InverterMetrics;
import net.konzol.easunjava.domain.inverter.Inverter;
import net.konzol.easunjava.domain.inverter.InverterRepository;
import net.konzol.easunjava.domain.statistics.Statistics;
import net.konzol.easunjava.domain.statistics.StatisticsRepository;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class StatusService {

    private static final double POWER_FACTOR = 360.0;
    private final SerialConnectionService serialConnectionService;
    private final StatisticsRepository statisticsRepository;
    private final InverterRepository inverterRepository;
    private final InverterMetrics inverterMetrics;
    private final ObjectMapper mapper;

    @Getter
    private final List<DeviceStatus> deviceStatusList;

    private StatusService(@Autowired SerialConnectionService serialConnectionService,
                          @Autowired StatisticsRepository statisticsRepository,
                          @Autowired InverterRepository inverterRepository,
                          @Autowired InverterMetrics inverterMetrics) {
        this.serialConnectionService = serialConnectionService;
        this.statisticsRepository = statisticsRepository;
        this.inverterRepository = inverterRepository;
        this.inverterMetrics = inverterMetrics;

        deviceStatusList = new ArrayList<>();
        mapper = new ObjectMapper();
    }

    @Scheduled(cron = "*/10 * * * * *")
    private void scheduleUpdate() {
        serialConnectionService.sendBytes(HexUtils.fromHexString("5150494753B7A90D"));
    }

    @EventListener
    public void serialMessageEventHandler(SerialMessageEvent event) {
        String[] data = event.getMessage().split(" ");

        if (data.length != 21) {
            return;
        }

        Optional<Inverter> inverterOptional = inverterRepository.findByPortNumber(event.getPortNumber());
        if (!inverterOptional.isPresent()) {
            return;
        }

        this.logData(data);

        Optional<DeviceStatus> deviceStatusOptional = deviceStatusList.stream()
                .filter(deviceStatus -> deviceStatus.getInverter().getPortNumber().equals(event.getPortNumber()))
                .findAny();

        DeviceStatus deviceStatus = null;

        if (deviceStatusOptional.isPresent()) {
            deviceStatus = this.updateValues(deviceStatusOptional.get(), data);
        } else {
            deviceStatus = DeviceStatus.builder()
                    .inverter(inverterOptional.get())
                    .build();
            this.updateValues(deviceStatus, data);

            deviceStatusList.add(deviceStatus);
            inverterMetrics.registerDeviceStatusMetrics(deviceStatus);
        }

        this.updateDatabase(deviceStatus);
    }


    private DeviceStatus updateValues(DeviceStatus deviceStatus, String[] data) {
        deviceStatus.setGridVoltage(Double.parseDouble(data[0].replace("(", "")));
        deviceStatus.setGridFrequency(Double.parseDouble(data[1]));
        deviceStatus.setOutputVoltage(Double.parseDouble(data[2]));
        deviceStatus.setOutputFrequency(Double.parseDouble(data[3]));
        deviceStatus.setOutputApparentPower(Integer.parseInt(data[4]));
        deviceStatus.setOutputActivePower(new AtomicInteger(Integer.parseInt(data[5])));
        deviceStatus.setOutputLoadPercent(Integer.parseInt(data[6]));
        deviceStatus.setBusVoltage(Integer.parseInt(data[7]));
        deviceStatus.setBatteryVoltage(Double.parseDouble(data[8]));
        deviceStatus.setBatteryChargeCurrent(Integer.parseInt(data[9]));
        deviceStatus.setBatteryStateOfCharge(Integer.parseInt(data[10]));
        deviceStatus.setInverterHeatSinkTemperature(Integer.parseInt(data[11]));
        deviceStatus.setSolarInputCurrent(Integer.parseInt(data[12]));
        deviceStatus.setSolarInputVoltage(Double.parseDouble(data[13]));
        deviceStatus.setBatteryVoltageScc(Double.parseDouble(data[14]));
        deviceStatus.setBatteryDischargeCurrent(Integer.parseInt(data[15]));

        this.logMessage(deviceStatus);

        return deviceStatus;
    }

    private void logMessage(DeviceStatus deviceStatus) {
        try {
            log.info("DeviceStatus: {}", mapper.writeValueAsString(deviceStatus));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void logData(String[] data) {
        try {
            log.info("MessageParsing: {}", mapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void updateDatabase(DeviceStatus deviceStatus) {

        Double solarPower = deviceStatus.getBatteryVoltageScc() * deviceStatus.getSolarInputCurrent();
        Double batteryCharged = deviceStatus.getBatteryVoltage() * deviceStatus.getBatteryChargeCurrent();
        Double batteryDischarged = deviceStatus.getBatteryVoltage() * deviceStatus.getBatteryDischargeCurrent();
        Double activeConsumption = deviceStatus.getOutputActivePower().doubleValue();

        solarPower = solarPower / POWER_FACTOR;
        batteryCharged = batteryCharged / POWER_FACTOR;
        batteryDischarged = batteryDischarged / POWER_FACTOR;
        activeConsumption = activeConsumption / POWER_FACTOR;

        Date today = new Date();

        Optional<Statistics> statisticsOptional =
                statisticsRepository.findByDateAndInverter(today, deviceStatus.getInverter());

        if (statisticsOptional.isPresent()) {
            Statistics statistics = statisticsOptional.get();

            statistics.setSolarPower(statistics.getSolarPower() + solarPower);
            statistics.setBatteryCharged(statistics.getBatteryCharged() + batteryCharged);
            statistics.setBatteryDischarged(statistics.getBatteryDischarged() + batteryDischarged);
            statistics.setActiveConsumption(statistics.getActiveConsumption() + activeConsumption);

            statisticsRepository.save(statistics);
        } else {
            Statistics statistics = Statistics.builder()
                    .date(today)
                    .inverter(deviceStatus.getInverter())
                    .solarPower(solarPower)
                    .batteryCharged(batteryCharged)
                    .batteryDischarged(batteryDischarged)
                    .activeConsumption(activeConsumption)
                    .build();

            statisticsRepository.save(statistics);
        }
    }
}
