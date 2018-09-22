package org.javadevday.gas.app.application;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.javadevday.gas.app.model.map.MapService;
import org.javadevday.gas.app.model.price.GasPriceService;
import org.javadevday.gas.app.model.stations.GasStation;
import org.javadevday.gas.app.model.stations.GasStation.Location;
import org.javadevday.gas.app.model.stations.GasStationService;
import org.javadevday.gas.app.port.adapter.http.BlockingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GasStationApplicationService {

  @Autowired
  GasPriceService gasPriceService;
  @Autowired
  MapService mapService;
  @Autowired
  GasStationService gasStationService;
  @Autowired
  BlockingService blockingService;
  ExecutorService service = Executors.newFixedThreadPool(100);

  public Flux<GasStation> getStationsNear(double longitude, double latitude, int radio) {
    return gasStationService.getStationsNear(longitude, latitude, radio)
        .buffer(20)
        .flatMap(gasStations ->
            Flux.zip(Flux.fromIterable(gasStations),
                gasPriceService.getPrices(gasStations),
                mapService.getDistance(gasStations, new Location(longitude, latitude)))
                .map(objects -> {
                  GasStation station = objects.getT1();
                  station.setDestination(objects.getT3());
                  station.setPrices(objects.getT2());
                  return station;
                })
        );

  }

  public Mono<String> callBlockingService() {
    return Mono
        .fromCompletionStage(CompletableFuture.supplyAsync(() -> blockingService.blockingCall(), service));
  }
}
