package org.javadevday.gas.location.model;

import org.javadevday.gas.location.application.GetGasStationsCommand;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GasStationService {

  public Flux<GasStation> streamGasStations(GetGasStationsCommand command,
      ReactiveMongoTemplate template) {
    return template.find(createQuery(command), GasStation.class);
  }

  private Query createQuery(GetGasStationsCommand command) {
    GeoJsonPoint point = new GeoJsonPoint(command.getLongitude(), command.getLatitude());
    return new Query(
        Criteria.where("location").near(point).maxDistance(command.getRadio()).minDistance(0))
        .with(new Sort(Sort.Direction.ASC, "_id"));
  }
}
