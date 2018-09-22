package org.javadevday.gas.app.resources;

import org.javadevday.gas.app.application.GasStationApplicationService;
import org.javadevday.gas.app.model.stations.GasStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/gasStations")
public class GasStationsResource {

  @Autowired
  GasStationApplicationService service;

  @GetMapping(value = "/near", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<GasStation> getGasStationsNearBy(
      @RequestParam("longitude") double longitude,
      @RequestParam("latitude") double latitude,
      @RequestParam(value = "radio", defaultValue = "1000") int radio) {
    return service.getStationsNear(longitude, latitude, radio);
  }

  @GetMapping(value = "/block")
  public Mono<String> block() {
    return service.callBlockingService();
  }
}
