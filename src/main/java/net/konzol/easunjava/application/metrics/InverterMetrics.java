package net.konzol.easunjava.application.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.konzol.easunjava.application.inverter.DeviceStatus;
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

        registerGauge(inverter, "easun_grid_voltage", deviceStatus, DeviceStatus::getGridVoltage);
        registerGauge(inverter, "easun_grid_frequency", deviceStatus, DeviceStatus::getGridFrequency);
        registerGauge(inverter, "easun_output_voltage", deviceStatus, DeviceStatus::getOutputVoltage);
        registerGauge(inverter, "easun_output_frequency", deviceStatus, DeviceStatus::getOutputFrequency);
        registerGauge(inverter, "easun_output_apparent_power", deviceStatus, DeviceStatus::getOutputApparentPower);
        registerGauge(inverter, "easun_output_active_power", deviceStatus, DeviceStatus::getOutputActivePower);
        registerGauge(inverter, "easun_bus_voltage", deviceStatus, DeviceStatus::getBusVoltage);
        registerGauge(inverter, "easun_output_load_percent", deviceStatus, DeviceStatus::getOutputLoadPercent);
        registerGauge(inverter, "easun_battery_voltage", deviceStatus, DeviceStatus::getBatteryVoltage);
        registerGauge(inverter, "easun_battery_charge_current", deviceStatus, DeviceStatus::getBatteryChargeCurrent);
        registerGauge(inverter, "easun_battery_state_of_charge", deviceStatus, DeviceStatus::getBatteryStateOfCharge);
        registerGauge(inverter, "easun_inverter_heat_sink_temp", deviceStatus, DeviceStatus::getInverterHeatSinkTemperature);
        registerGauge(inverter, "easun_solar_input_current", deviceStatus, DeviceStatus::getSolarInputCurrent);
        registerGauge(inverter, "easun_solar_input_voltage", deviceStatus, DeviceStatus::getSolarInputVoltage);
        registerGauge(inverter, "easun_battery_voltage_scc", deviceStatus, DeviceStatus::getBatteryVoltageScc);
        registerGauge(inverter, "easun_battery_discharge_current", deviceStatus, DeviceStatus::getBatteryDischargeCurrent);

    }

    private void registerGauge(Inverter inverter,
                               String metric,
                               DeviceStatus deviceStatus,
                               ToDoubleFunction<DeviceStatus> function) {
        Gauge.builder(metric, deviceStatus, function)
                .tag("inverter", inverter.getPortNumber().toString())
                .register(meterRegistry);
    }
}
