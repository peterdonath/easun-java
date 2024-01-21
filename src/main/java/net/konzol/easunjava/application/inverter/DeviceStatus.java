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
    private Double gridVoltage;
    private Double gridFrequency;
    private Double outputVoltage;
    private Double outputFrequency;
    private Integer outputApparentPower;
    private Integer outputActivePower;
    private Integer busVoltage;
    private Integer outputLoadPercent;
    private Double batteryVoltage;
    private Integer batteryChargeCurrent;
    private Integer batteryStateOfCharge;
    private Integer inverterHeatSinkTemperature;
    private Integer solarInputCurrent;
    private Double solarInputVoltage;
    private Double batteryVoltageScc;
    private Integer batteryDischargeCurrent;
}
