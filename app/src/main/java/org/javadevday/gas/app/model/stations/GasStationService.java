package org.javadevday.gas.app.model.stations;

import reactor.core.publisher.Flux;

public interface GasStationService {

  Flux<GasStation> getStationsNear(double longitude, double latitude, int radio);
}
