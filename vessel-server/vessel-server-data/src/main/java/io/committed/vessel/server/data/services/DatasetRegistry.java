package io.committed.vessel.server.data.services;

import java.util.List;

import io.committed.vessel.server.data.dataset.Dataset;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DatasetRegistry {

  private final List<Dataset> datasets;

  public DatasetRegistry(final List<Dataset> datasets) {
    this.datasets = datasets;
  }

  public Flux<Dataset> getCorpora() {
    return Flux.fromIterable(datasets);
  }

  public Mono<Dataset> findById(final String id) {
    return getCorpora()
        .filter(c -> c.getId().equalsIgnoreCase(id))
        .next();
  }
}
