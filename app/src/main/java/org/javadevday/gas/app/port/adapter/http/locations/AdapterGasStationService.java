package org.javadevday.gas.app.port.adapter.http.locations;

import org.javadevday.gas.app.model.stations.GasStation;
import org.javadevday.gas.app.model.stations.GasStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class AdapterGasStationService implements GasStationService {

  @Value("${app.locations.url}")
  private String url;


  @Autowired
  WebClient client;

  @Override
  public Flux<GasStation> getStationsNear(double longitude, double latitude, int radio) {
    return client.get().uri(getUrl(longitude, latitude, radio))
        .accept(MediaType.APPLICATION_STREAM_JSON)
        .exchange()
        .flatMapMany(clientResponse -> clientResponse.bodyToFlux(GasStation.class));
  }


  private String getUrl(double longitude, double latitude, int radio) {
    return url + "?longitude=" + longitude + "&latitude=" + latitude + "&radio=" + radio;

  }
}
