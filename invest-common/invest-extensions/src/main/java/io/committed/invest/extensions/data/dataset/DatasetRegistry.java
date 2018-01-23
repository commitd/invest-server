package io.committed.invest.extensions.data.dataset;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface DatasetRegistry {

  Flux<Dataset> getDatasets();

  Mono<Dataset> findById(String id);

}
