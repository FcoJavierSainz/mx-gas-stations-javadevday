package org.javadevday.gas.price.port.adapter.jpa;

import com.ibm.reactive.jpa.Database;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import java.sql.SQLTransientException;
import java.time.Duration;
import java.util.List;
import org.javadevday.gas.price.model.station.GasPriceRepository;
import org.javadevday.gas.price.model.station.GasStationPrices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.retry.Backoff;
import reactor.retry.Retry;

@Repository
public class JpaGasPriceRepository implements GasPriceRepository {

  @Autowired
  private Database database;

  @Autowired
  private CircuitBreaker circuitBreaker;

  private static final String query = "from GasStationPrices where placeId "
      + "in (:places) ORDER BY placeId";

  @Override
  public Flux<GasStationPrices> getPrices(List<Integer> places) {
    return database.stream(query, GasStationPrices.class)
        .addParameter("places", places)
        .fetchSize(10)
        .flux()
        .retryWhen(retry())
        .transform(CircuitBreakerOperator.of(circuitBreaker));
  }

  private Retry<GasStationPrices> retry() {
    return Retry.<GasStationPrices>anyOf(SQLTransientException.class)
        .retryMax(3)
        .backoff(Backoff.fixed(Duration.ofMillis(100)));
  }
}
