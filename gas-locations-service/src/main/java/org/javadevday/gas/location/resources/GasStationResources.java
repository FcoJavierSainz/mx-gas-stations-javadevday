package org.javadevday.gas.location.resources;

import org.javadevday.gas.location.application.GasStationApplicationService;
import org.javadevday.gas.location.application.GetGasStationsCommand;
import org.javadevday.gas.location.model.GasStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/locations")
public class GasStationResources {

  @Autowired
  GasStationApplicationService service;

  @GetMapping(value = "/near", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<GasStation> getLocationsNearTo(@RequestParam("longitude") double longitude,
      @RequestParam("latitude") double latitude,
      @RequestParam(value = "radio", defaultValue = "1000") int radio) {
    return service.getGasStations(new GetGasStationsCommand(longitude, latitude, radio));
  }
}
