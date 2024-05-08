package net.konzol.easunjava.application.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.konzol.easunjava.application.inverter.DeviceStatus;
import net.konzol.easunjava.controller.jkbms.JkBmsData;
import net.konzol.easunjava.domain.inverter.Inverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.ToDoubleFunction;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class InverterMetrics {

    private final MeterRegistry meterRegistry;

    public void updateDeviceStatusMetrics(DeviceStatus deviceStatus) {
        Inverter inverter = deviceStatus.getInverter();

        registerInverterGauge(inverter, "easun_grid_voltage", deviceStatus, DeviceStatus::getGridVoltage);
        registerInverterGauge(inverter, "easun_grid_frequency", deviceStatus, DeviceStatus::getGridFrequency);
        registerInverterGauge(inverter, "easun_output_voltage", deviceStatus, DeviceStatus::getOutputVoltage);
        registerInverterGauge(inverter, "easun_output_frequency", deviceStatus, DeviceStatus::getOutputFrequency);
        registerInverterGauge(inverter, "easun_output_apparent_power", deviceStatus, DeviceStatus::getOutputApparentPower);
        registerInverterGauge(inverter, "easun_output_active_power", deviceStatus, DeviceStatus::getOutputActivePower);
        registerInverterGauge(inverter, "easun_bus_voltage", deviceStatus, DeviceStatus::getBusVoltage);
        registerInverterGauge(inverter, "easun_output_load_percent", deviceStatus, DeviceStatus::getOutputLoadPercent);
        registerInverterGauge(inverter, "easun_battery_voltage", deviceStatus, DeviceStatus::getBatteryVoltage);
        registerInverterGauge(inverter, "easun_battery_charge_current", deviceStatus, DeviceStatus::getBatteryChargeCurrent);
        registerInverterGauge(inverter, "easun_battery_state_of_charge", deviceStatus, DeviceStatus::getBatteryStateOfCharge);
        registerInverterGauge(inverter, "easun_inverter_heat_sink_temp", deviceStatus, DeviceStatus::getInverterHeatSinkTemperature);
        registerInverterGauge(inverter, "easun_solar_input_current", deviceStatus, DeviceStatus::getSolarInputCurrent);
        registerInverterGauge(inverter, "easun_solar_input_voltage", deviceStatus, DeviceStatus::getSolarInputVoltage);
        registerInverterGauge(inverter, "easun_battery_voltage_scc", deviceStatus, DeviceStatus::getBatteryVoltageScc);
        registerInverterGauge(inverter, "easun_battery_discharge_current", deviceStatus, DeviceStatus::getBatteryDischargeCurrent);

    }

    private void registerInverterGauge(Inverter inverter,
                                       String metric,
                                       DeviceStatus deviceStatus,
                                       ToDoubleFunction<DeviceStatus> function) {
        Gauge.builder(metric, deviceStatus, function)
                .tag("inverter", inverter.getPortNumber().toString())
                .register(meterRegistry);
    }

    public void updateJkBmsMetrics(JkBmsData jkBmsData) {
        registerBmsGauge("jk_b2a20s20p", "bms_cell_voltage_average", jkBmsData, JkBmsData::getAverageCellVoltage);
        registerBmsGauge("jk_b2a20s20p", "bms_cell_voltage_delta", jkBmsData, JkBmsData::getDeltaCellVoltage);
        registerBmsGauge("jk_b2a20s20p", "bms_battery_power", jkBmsData, JkBmsData::getBatteryPower);
        registerBmsGauge("jk_b2a20s20p", "bms_capacity", jkBmsData, JkBmsData::getCapacityRemain);
        registerBmsGauge("jk_b2a20s20p", "bms_cycle_count", jkBmsData, JkBmsData::getCycleCount);
        registerBmsGauge("jk_b2a20s20p", "bms_soc", jkBmsData, JkBmsData::getPercentRemain);
    }

    private void registerBmsGauge(String bms,
                                  String metric,
                                  JkBmsData bmsData,
                                  ToDoubleFunction<JkBmsData> function) {
        Gauge.builder(metric, bmsData, function)
            .tag("bms", bms)
            .register(meterRegistry);
    }
}
