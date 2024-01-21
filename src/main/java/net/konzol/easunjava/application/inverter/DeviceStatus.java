package net.konzol.easunjava.application.inverter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.konzol.easunjava.domain.inverter.Inverter;

@Builder
@Getter
@Setter
public class DeviceStatus {

    private Inverter inverter;
    private Double gridVoltage = 0.0;
    private Double gridFrequency = 0.0;
    private Double outputVoltage = 0.0;
    private Double outputFrequency = 0.0;
    private Integer outputApparentPower = 0;
    private Integer outputActivePower = 0;
    private Integer busVoltage = 0;
    private Integer outputLoadPercent = 0;
    private Double batteryVoltage = 0.0;
    private Integer batteryChargeCurrent = 0;
    private Integer batteryStateOfCharge = 0;
    private Integer inverterHeatSinkTemperature = 0;
    private Integer solarInputCurrent = 0;
    private Double solarInputVoltage = 0.0;
    private Double batteryVoltageScc = 0.0;
    private Integer batteryDischargeCurrent = 0;
}
