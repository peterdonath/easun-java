package net.konzol.easunjava.application.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.konzol.easunjava.application.inverter.DeviceStatus;
import net.konzol.easunjava.domain.inverter.Inverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class InverterMetrics {

    private final MeterRegistry meterRegistry;

    public void updateDeviceStatusMetrics(DeviceStatus deviceStatus) {
        Inverter inverter = deviceStatus.getInverter();

        registerDoubleGauge(inverter, "grid_voltage", deviceStatus.getGridVoltage());
        registerDoubleGauge(inverter, "grid_frequency", deviceStatus.getGridFrequency());
        registerDoubleGauge(inverter, "output_voltage", deviceStatus.getOutputVoltage());
        registerDoubleGauge(inverter, "output_frequency", deviceStatus.getOutputFrequency());
        registerIntegerGauge(inverter, "output_apparent_power", deviceStatus.getOutputApparentPower());
        // registerIntegerGauge(inverter, "output_active_power", deviceStatus.getOutputActivePower());
        registerIntegerGauge(inverter, "bus_voltage", deviceStatus.getBusVoltage());
        registerIntegerGauge(inverter, "output_load_percent", deviceStatus.getOutputLoadPercent());
        registerDoubleGauge(inverter, "battery_voltage", deviceStatus.getBatteryVoltage());
        registerIntegerGauge(inverter, "battery_charge_current", deviceStatus.getBatteryChargeCurrent());
        registerIntegerGauge(inverter, "battery_state_of_charge", deviceStatus.getBatteryStateOfCharge());
        registerIntegerGauge(inverter, "inverter_heat_sink_temp", deviceStatus.getInverterHeatSinkTemperature());
        registerIntegerGauge(inverter, "solar_input_current", deviceStatus.getSolarInputCurrent());
        registerDoubleGauge(inverter, "solar_input_voltage", deviceStatus.getSolarInputVoltage());
        registerDoubleGauge(inverter, "battery_voltage_scc", deviceStatus.getBatteryVoltageScc());
        registerIntegerGauge(inverter, "battery_discharge_current", deviceStatus.getBatteryDischargeCurrent());

        Gauge.builder("output_active_power", deviceStatus, DeviceStatus::getOutputActivePower)
                .tag("inverter", inverter.getPortNumber().toString())
                .register(meterRegistry);
    }

    private void registerDoubleGauge(Inverter inverter, String metric, Double value) {
        Gauge gauge = Gauge.builder(metric, value, Double::valueOf)
                .tag("inverter", inverter.getPortNumber().toString())
                .register(meterRegistry);
    }

    private void registerIntegerGauge(Inverter inverter, String metric, Integer value) {
        Gauge gauge = Gauge.builder(metric, value, Double::valueOf)
                .tag("inverter", inverter.getPortNumber().toString())
                .register(meterRegistry);
    }

}
