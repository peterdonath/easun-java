package net.konzol.easunjava.controller.jkbms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class JkBmsData {

  private double voltageCell01;
  private double voltageCell02;
  private double voltageCell03;
  private double voltageCell04;
  private double voltageCell05;
  private double voltageCell06;
  private double voltageCell07;
  private double voltageCell08;
  private double voltageCell09;
  private double voltageCell10;
  private double voltageCell11;
  private double voltageCell12;
  private double voltageCell13;
  private double voltageCell14;
  private double voltageCell15;
  private double voltageCell16;
  private double averageCellVoltage;
  private double deltaCellVoltage;
  private double currentBalancer;
  private double batteryVoltage;
  private double batteryPower;
  private double balanceCurrent;
  private double batteryT1;
  private double batteryT2;
  private double percentRemain;
  private double capacityRemain;
  private double nominalCapacity;
  private double cycleCount;
  private double currentCharge;
  private double currentDischarge;

}
