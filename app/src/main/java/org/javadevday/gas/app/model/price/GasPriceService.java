package org.javadevday.gas.app.model.price;

import java.util.List;
import org.javadevday.gas.app.model.stations.GasStation;
import reactor.core.publisher.Flux;

public interface GasPriceService {
    Flux<GasStationPrices> getPrices(List<GasStation> stations);
}
