package org.javadevday.gas.app.model.stations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javadevday.gas.app.model.map.Destination;
import org.javadevday.gas.app.model.price.GasStationPrices;

@Data
public class GasStation {

  private String id;
  private String name;
  private String address;
  private String category;
  private String brand;
  private Location location;

  private GasStationPrices prices;
  private Destination destination;


  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Location {

    private double x;
    private double y;
  }
}
