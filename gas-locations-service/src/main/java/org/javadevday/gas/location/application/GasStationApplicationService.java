package org.javadevday.gas.location.application;

import org.javadevday.gas.location.model.GasStation;
import org.javadevday.gas.location.model.GasStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GasStationApplicationService {

  @Autowired
  GasStationService gasStationService;

  @Autowired
  ReactiveMongoTemplate template;

  public Flux<GasStation> getGasStations(GetGasStationsCommand command) {
    return gasStationService.streamGasStations(command, template);
  }
}
