package org.javadevday.gas.app.port.adapter.http.maps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.javadevday.gas.app.model.map.Destination;
import org.javadevday.gas.app.model.map.MapService;
import org.javadevday.gas.app.model.stations.GasStation;
import org.javadevday.gas.app.model.stations.GasStation.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class GoogleMapsService implements MapService {

  @Autowired
  WebClient client;

  @Value("${app.maps.url}")
  private String mapsUrl;
  @Value("${app.maps.api-key}")
  private String apiKey;

  @Override
  public Flux<Destination> getDistance(List<GasStation> gasStations, Location origin) {
    return client.get().uri(getUrl(gasStations, origin))
        .retrieve()
        .bodyToMono(JsonNode.class)
        .flatMapMany(this::toList);
  }

  private Flux<Destination> toList(JsonNode node) {
    if (!"OK".equals(node.get("status").asText())) {
      throw new IllegalArgumentException("Invalid coordinates");
    }
    ArrayNode addresses = (ArrayNode) node.get("destination_addresses");
    String origin = node.get("origin_addresses").get(0).asText();
    ArrayNode rows = (ArrayNode) node.get("rows").get(0).get("elements");
    return Flux.fromIterable(mergeData(addresses, rows, origin));
  }

  private List<Destination> mergeData(ArrayNode addresses, ArrayNode rows, String origin) {
    return IntStream.range(0, addresses.size())
        .mapToObj(index -> toDistance(addresses.get(index), rows.get(index), origin))
        .collect(Collectors.toList());
  }

  private Destination toDistance(JsonNode address, JsonNode data, String origin) {
    JsonNode distance = data.get("distance");
    JsonNode duration = data.get("duration");
    return new Destination(origin, address.asText(), duration.get("text").asText(),
        distance.get("text").asText(), duration.get("value").asInt(),
        distance.get("value").asInt());
  }

  private String getUrl(List<GasStation> gasStations, Location origin) {
    StringBuilder url = new StringBuilder(mapsUrl);
    String destinations = gasStations.stream()
        .map(gasStation -> gasStation.getLocation().getY() + "," + gasStation.getLocation()
            .getX())
        .collect(Collectors.joining("|"));
    return url.append("?origins=").append(origin.getY()).append(",")
        .append(origin.getX())
        .append("&destinations=").append(destinations)
        .append("&key=").append(apiKey).toString();
  }
}
