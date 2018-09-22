package org.javadevday.gas.location.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "places")
@Data
public class GasStation {

  @Id
  private String id;
  private String name;
  private String address;
  private String category;
  private String brand;
  private GeoJsonPoint location;
}
