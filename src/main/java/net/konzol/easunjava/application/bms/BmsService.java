package net.konzol.easunjava.application.bms;

import lombok.RequiredArgsConstructor;
import net.konzol.easunjava.application.metrics.InverterMetrics;
import net.konzol.easunjava.controller.jkbms.JkBmsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BmsService {

  private BmsData bmsData;

  private final InverterMetrics inverterMetrics;

  public void updateBmsData(JkBmsData jkBmsData) {
    if (bmsData == null) {
      bmsData = BmsData.builder()
          .averageCellVoltage(jkBmsData.getAverageCellVoltage())
          .deltaCellVoltage(jkBmsData.getDeltaCellVoltage())
          .batteryPower(jkBmsData.getBatteryPower())
          .capacityRemain(jkBmsData.getCapacityRemain())
          .cycleCount(jkBmsData.getCycleCount())
          .percentRemain(jkBmsData.getPercentRemain())
          .build();

      inverterMetrics.updateJkBmsMetrics(bmsData);
    } else {
      bmsData.update(jkBmsData);
    }
  }
}
