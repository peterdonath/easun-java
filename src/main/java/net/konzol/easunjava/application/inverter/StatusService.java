package net.konzol.easunjava.application.inverter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
        deviceStatus.setGridVoltage(Double.parseDouble(data[0].replace("(","")));
        deviceStatus.setGridFrequency(Double.parseDouble(data[1]));
        deviceStatus.setOutputVoltage(Double.parseDouble(data[2]));
        deviceStatus.setOutputFrequency(Double.parseDouble(data[3]));
        deviceStatus.setOutputApparentPower(Integer.parseInt(data[4]));
        deviceStatus.setOutputActivePower(Integer.parseInt(data[5]));
        deviceStatus.setBusVoltage(Integer.parseInt(data[6]));
        deviceStatus.setBatteryVoltage(Double.parseDouble(data[7]));
        deviceStatus.setBatteryChargeCurrent(Integer.parseInt(data[8]));
        deviceStatus.setBatteryStateOfCharge(Integer.parseInt(data[9]));
        deviceStatus.setInverterHeatSinkTemperature(Integer.parseInt(data[10]));
        deviceStatus.setSolarInputCurrent(Integer.parseInt(data[11]));
        deviceStatus.setSolarInputVoltage(Double.parseDouble(data[12]));
        deviceStatus.setBatteryVoltageScc(Double.parseDouble(data[13]));
        deviceStatus.setBatteryDischargeCurrent(Integer.parseInt(data[14]));
    }
}
