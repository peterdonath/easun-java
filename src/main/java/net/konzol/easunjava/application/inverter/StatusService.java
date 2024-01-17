package net.konzol.easunjava.application.inverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class StatusService {

    private final SerialConnection serialConnection;

    @Getter
    private DeviceStatus deviceStatus = DeviceStatus.builder().build();

    @Scheduled(cron = "*/10 * * * * *")
    private void scheduleUpdate() {
        serialConnection.sendBytes(HexUtils.fromHexString("5150494753B7A90D"));
    }

    @EventListener
    public void serialMessageEventHandler(SerialMessageEvent event) {
        String[] data = event.getMessage().split(" ");

        if (data.length != 16) {
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            log.info("MessageParsing: {}", mapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        deviceStatus.setGridVoltage(Double.parseDouble(data[0].replace("(","")));
        deviceStatus.setGridFrequency(Double.parseDouble(data[1]));
        deviceStatus.setOutputVoltage(Double.parseDouble(data[2]));
        deviceStatus.setOutputFrequency(Double.parseDouble(data[3]));
        deviceStatus.setOutputApparentPower(Integer.parseInt(data[4]));
        deviceStatus.setOutputActivePower(Integer.parseInt(data[5]));
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

        try {
            log.info("DeviceStatus: {}", mapper.writeValueAsString(deviceStatus));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
