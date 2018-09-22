package org.javadevday.gas.blocking.station;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "gasStations.prices")
@Data
public class GasStationPrices {

  @Id
  private int placeId;
  private Double regularPrice;
  private Instant regularUpdatedAt;
  private Double premiumPrice;
  private Instant premiumUpdatedAt;
  private Double dieselPrice;
  private Instant dieselUpdatedAt;
}