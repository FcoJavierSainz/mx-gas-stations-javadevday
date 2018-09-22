package org.javadevday.gas.app.model.map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Destination {

  private String originAddress;
  private String destinationAddress;
  private String durationText;
  private String distanceText;
  private int duration;
  private int distance;

}
