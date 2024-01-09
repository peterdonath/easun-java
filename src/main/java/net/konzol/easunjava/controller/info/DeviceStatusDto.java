package net.konzol.easunjava.controller.info;

import lombok.Builder;
import lombok.Getter;
import net.konzol.easunjava.application.inverter.DeviceStatus;

@Getter
@Builder
public class DeviceStatusDto {

    private String gridVoltage;
    private String gridFrequency;
    private String outputVoltage;
    private String outputFrequency;
    private String outputApparentPower;
    private String outputActivePower;
    private String outputLoadPercent;
    private String busVoltage;
    private String batteryVoltage;
    private String batteryChargeCurrent;
    private String batteryStateOfCharge;
    private String inverterHeatSinkTemperature;
    private String solarInputCurrent;
    private String solarInputVoltage;
    private String batteryVoltageScc;
    private String batteryDischargeCurrent;

    public static DeviceStatusDto of(DeviceStatus deviceStatus){
        return DeviceStatusDto.builder()
                .gridVoltage(deviceStatus.getGridVoltage().toString().concat("V"))
                .gridFrequency(deviceStatus.getGridFrequency().toString().concat("Hz"))
                .outputVoltage(deviceStatus.getOutputVoltage().toString().concat("V"))
                .outputFrequency(deviceStatus.getOutputFrequency().toString().concat("Hz"))
                .outputApparentPower(deviceStatus.getOutputApparentPower().toString().concat("VA"))
                .outputActivePower(deviceStatus.getOutputActivePower().toString().concat("W"))
                .outputLoadPercent(deviceStatus.getOutputLoadPercent().toString().concat("%"))
                .busVoltage(deviceStatus.getBusVoltage().toString().concat("V"))
                .batteryVoltage(deviceStatus.getBatteryVoltage().toString().concat("V"))
                .batteryChargeCurrent(deviceStatus.getBatteryChargeCurrent().toString().concat("A"))
                .batteryStateOfCharge(deviceStatus.getBatteryStateOfCharge().toString().concat("%"))
                .inverterHeatSinkTemperature(deviceStatus.getInverterHeatSinkTemperature().toString().concat("C"))
                .solarInputCurrent(deviceStatus.getSolarInputCurrent().toString().concat("A"))
                .solarInputVoltage(deviceStatus.getSolarInputVoltage().toString().concat("V"))
                .batteryVoltageScc(deviceStatus.getBatteryVoltageScc().toString().concat("%"))
                .batteryDischargeCurrent(deviceStatus.getBatteryDischargeCurrent().toString().concat("A"))
                .build();
    }
}
