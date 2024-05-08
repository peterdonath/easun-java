package net.konzol.easunjava.controller.jkbms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class JkBmsData {

  @JsonProperty("voltage_cell01")
  private double voltageCell01;

  @JsonProperty("voltage_cell02")
  private double voltageCell02;

  @JsonProperty("voltage_cell03")
  private double voltageCell03;

  @JsonProperty("voltage_cell04")
  private double voltageCell04;

  @JsonProperty("voltage_cell05")
  private double voltageCell05;

  @JsonProperty("voltage_cell06")
  private double voltageCell06;

  @JsonProperty("voltage_cell07")
  private double voltageCell07;

  @JsonProperty("voltage_cell08")
  private double voltageCell08;

  @JsonProperty("voltage_cell09")
  private double voltageCell09;

  @JsonProperty("voltage_cell10")
  private double voltageCell10;

  @JsonProperty("voltage_cell11")
  private double voltageCell11;

  @JsonProperty("voltage_cell12")
  private double voltageCell12;

  @JsonProperty("voltage_cell13")
  private double voltageCell13;

  @JsonProperty("voltage_cell14")
  private double voltageCell14;

  @JsonProperty("voltage_cell15")
  private double voltageCell15;

  @JsonProperty("voltage_cell16")
  private double voltageCell16;

  @JsonProperty("average_cell_voltage")
  private double averageCellVoltage;

  @JsonProperty("delta_cell_voltage")
  private double deltaCellVoltage;

  @JsonProperty("current_balancer")
  private double currentBalancer;

  @JsonProperty("battery_voltage")
  private double batteryVoltage;

  @JsonProperty("battery_power")
  private double batteryPower;

  @JsonProperty("balance_current")
  private double balanceCurrent;

  @JsonProperty("battery_t1")
  private double batteryT1;

  @JsonProperty("battery_t2")
  private double batteryT2;

  @JsonProperty("percent_remain")
  private double percentRemain;

  @JsonProperty("capacity_remain")
  private double capacityRemain;

  @JsonProperty("nominal_capacity")
  private double nominalCapacity;

  @JsonProperty("cycle_count")
  private double cycleCount;

  @JsonProperty("current_charge")
  private double currentCharge;

  @JsonProperty("current_discharge")
  private double currentDischarge;

}
