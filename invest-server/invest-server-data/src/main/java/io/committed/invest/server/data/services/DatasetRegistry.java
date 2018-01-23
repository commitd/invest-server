package io.committed.invest.server.data.services;

import java.util.List;
import io.committed.invest.extensions.data.dataset.Dataset;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DatasetRegistry {

  private final List<Dataset> datasets;

  public DatasetRegistry(final List<Dataset> datasets) {
    this.datasets = datasets;
  }

  public Flux<Dataset> getDatasets() {
    return Flux.fromIterable(datasets);
  }

  public Mono<Dataset> findById(final String id) {
    return getDatasets().filter(c -> c.getId().equalsIgnoreCase(id)).next();
  }
}
