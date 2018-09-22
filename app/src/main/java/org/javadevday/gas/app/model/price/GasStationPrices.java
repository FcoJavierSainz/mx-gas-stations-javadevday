package org.javadevday.gas.app.model.price;

import java.time.Instant;
import lombok.Data;

@Data
public class GasStationPrices {

  private int placeId;
  private Double regularPrice;
  private Instant regularUpdatedAt;
  private Double premiumPrice;
  private Instant premiumUpdatedAt;
  private Double dieselPrice;
  private Instant dieselUpdatedAt;
}