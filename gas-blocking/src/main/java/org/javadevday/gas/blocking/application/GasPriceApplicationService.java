package org.javadevday.gas.blocking.application;

import java.util.Collection;
import java.util.List;
import org.javadevday.gas.blocking.station.GasPriceRepository;
import org.javadevday.gas.blocking.station.GasStationPrices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GasPriceApplicationService {

  @Autowired
  private GasPriceRepository repository;

  public Collection<GasStationPrices> getPrices(List<Integer> places) {
    return repository.getPrices(places);
  }
}
