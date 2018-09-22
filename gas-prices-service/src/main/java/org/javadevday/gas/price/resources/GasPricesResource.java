package org.javadevday.gas.price.resources;

import java.util.List;
import org.javadevday.gas.price.application.GasPriceApplicationService;
import org.javadevday.gas.price.model.station.GasStationPrices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/prices")
public class GasPricesResource {

  @Autowired
  private GasPriceApplicationService service;

  @GetMapping
  public Flux<GasStationPrices> getPrice(@RequestParam(value = "placeId") List<Integer> places) {
    System.out.println(places);
    return service.getPrices(places);
  }
}
