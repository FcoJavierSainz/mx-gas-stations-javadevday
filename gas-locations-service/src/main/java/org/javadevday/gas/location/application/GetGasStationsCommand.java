package org.javadevday.gas.location.application;

import lombok.Data;
import lombok.Value;

@Value
public class GetGasStationsCommand {

  private double longitude;
  private double latitude;
  private int radio;
}
