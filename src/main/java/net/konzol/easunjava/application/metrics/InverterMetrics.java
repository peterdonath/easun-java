package net.konzol.easunjava.application.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import net.konzol.easunjava.application.inverter.DeviceStatus;
import net.konzol.easunjava.domain.inverter.Inverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class InverterMetrics {

    private final MeterRegistry meterRegistry;

    public void registerDeviceStatusMetrics(DeviceStatus deviceStatus) {
        Inverter inverter = deviceStatus.getInverter();
        registerGauge(inverter, "grid_voltage", deviceStatus.getGridVoltage());
        registerGauge(inverter, "grid_frequency", deviceStatus.getGridFrequency());
        registerGauge(inverter, "output_voltage", deviceStatus.getOutputVoltage());
        registerGauge(inverter, "output_frequency", deviceStatus.getOutputFrequency());
        registerGauge(inverter, "output_apparent_power", deviceStatus.getOutputApparentPower());
        registerGauge(inverter, "output_active_power", deviceStatus.getOutputActivePower());
        registerGauge(inverter, "bus_voltage", deviceStatus.getBusVoltage());
        registerGauge(inverter, "output_load_percent", deviceStatus.getOutputLoadPercent());
        registerGauge(inverter, "battery_voltage", deviceStatus.getBatteryVoltage());
        registerGauge(inverter, "battery_charge_current", deviceStatus.getBatteryChargeCurrent());
        registerGauge(inverter, "battery_state_of_charge", deviceStatus.getBatteryStateOfCharge());
        registerGauge(inverter, "inverter_heat_sink_temp", deviceStatus.getInverterHeatSinkTemperature());
        registerGauge(inverter, "solar_input_current", deviceStatus.getSolarInputCurrent());
        registerGauge(inverter, "solar_input_voltage", deviceStatus.getSolarInputVoltage());
        registerGauge(inverter, "battery_voltage_scc", deviceStatus.getBatteryVoltageScc());
        registerGauge(inverter, "battery_discharge_current", deviceStatus.getBatteryDischargeCurrent());
    }

    private void registerGauge(Inverter inverter, String metric, Number value) {
        Gauge gauge = Gauge.builder(metric, value, Number::doubleValue)
                .tag("inverter", inverter.getPortNumber().toString())
                .register(meterRegistry);
    }
}
