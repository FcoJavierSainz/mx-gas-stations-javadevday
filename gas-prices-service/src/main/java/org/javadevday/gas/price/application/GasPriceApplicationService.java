package org.javadevday.gas.price.application;

import java.util.List;
import org.javadevday.gas.price.model.station.GasPriceRepository;
import org.javadevday.gas.price.model.station.GasStationPrices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GasPriceApplicationService {
  @Autowired
  private GasPriceRepository repository;

  public Flux<GasStationPrices> getPrices(List<Integer> places) {
    return repository.getPrices(places);
  }
}
