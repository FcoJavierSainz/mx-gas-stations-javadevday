package org.javadevday.gas.blocking.resources;

import java.util.Collection;
import java.util.List;
import org.javadevday.gas.blocking.application.GasPriceApplicationService;
import org.javadevday.gas.blocking.station.GasStationPrices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/prices")
public class GasPricesResource {

  @Autowired
  private GasPriceApplicationService service;

  @GetMapping
  public Collection<GasStationPrices> getPrice(
      @RequestParam(value = "placeId") List<Integer> places) {
    return service.getPrices(places);
  }
}
