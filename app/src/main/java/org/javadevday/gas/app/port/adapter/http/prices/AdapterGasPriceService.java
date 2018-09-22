package org.javadevday.gas.app.port.adapter.http.prices;

import java.util.List;
import java.util.stream.Collectors;
import org.javadevday.gas.app.model.price.GasPriceService;
import org.javadevday.gas.app.model.price.GasStationPrices;
import org.javadevday.gas.app.model.stations.GasStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class AdapterGasPriceService implements GasPriceService {

  @Value("${app.prices.url}")
  private String url;


  @Autowired
  WebClient client;

  @Override
  public Flux<GasStationPrices> getPrices(List<GasStation> stations) {
    System.out.println(getUrl(stations));
    return client.get().uri(getUrl(stations))
        .accept(MediaType.APPLICATION_STREAM_JSON)
        .exchange()
        .flatMapMany(clientResponse -> clientResponse.bodyToFlux(GasStationPrices.class));
  }

  private String getUrl(List<GasStation> stations) {
    String places = stations.stream()
        .map(gasStation -> "placeId=" + gasStation.getId())
        .collect(Collectors.joining("&"));
    return url + "?" + places;

  }
}
