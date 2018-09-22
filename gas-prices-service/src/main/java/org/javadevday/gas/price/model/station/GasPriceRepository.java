package org.javadevday.gas.price.model.station;

import java.util.List;
import reactor.core.publisher.Flux;

public interface GasPriceRepository {
    Flux<GasStationPrices> getPrices(List<Integer> places);
}
