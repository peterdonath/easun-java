package net.konzol.easunjava.application.bms;

import lombok.Builder;
import lombok.Getter;
import net.konzol.easunjava.controller.jkbms.JkBmsData;

@Builder
@Getter
public class BmsData {

  private double averageCellVoltage;
  private double deltaCellVoltage;
  private double batteryPower;
  private double capacityRemain;
  private double cycleCount;
  private double percentRemain;

  public void update(JkBmsData jkBmsData) {
    this.averageCellVoltage = jkBmsData.getAverageCellVoltage();
    this.deltaCellVoltage = jkBmsData.getDeltaCellVoltage();
    this.batteryPower = jkBmsData.getBatteryPower();
    this.capacityRemain = jkBmsData.getCapacityRemain();
    this.cycleCount = jkBmsData.getCycleCount();
    this.percentRemain = jkBmsData.getPercentRemain();
  }
}
