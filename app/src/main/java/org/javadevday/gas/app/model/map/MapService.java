package org.javadevday.gas.app.model.map;

import java.util.List;
import org.javadevday.gas.app.model.stations.GasStation;
import org.javadevday.gas.app.model.stations.GasStation.Location;
import reactor.core.publisher.Flux;

public interface MapService {

  Flux<Destination> getDistance(List<GasStation> gasStations, Location origin);
}
