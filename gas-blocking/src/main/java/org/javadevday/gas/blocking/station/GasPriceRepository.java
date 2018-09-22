package org.javadevday.gas.blocking.station;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GasPriceRepository extends JpaRepository<GasStationPrices, Integer> {

  @Query(value = "from GasStationPrices where placeId in (?1)")
  Collection<GasStationPrices> getPrices(List<Integer> places);
}
