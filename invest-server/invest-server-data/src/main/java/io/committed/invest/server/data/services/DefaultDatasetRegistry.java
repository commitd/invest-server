package io.committed.invest.server.data.services;

import java.util.List;
import io.committed.invest.extensions.data.dataset.Dataset;
import io.committed.invest.extensions.data.dataset.DatasetRegistry;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DefaultDatasetRegistry implements DatasetRegistry {

  private final List<Dataset> datasets;

  public DefaultDatasetRegistry(final List<Dataset> datasets) {
    this.datasets = datasets;
  }

  /*
   * (non-Javadoc)
   *
   * @see io.committed.invest.server.data.services.DatasetRegistryIntf#getDatasets()
   */
  @Override
  public Flux<Dataset> getDatasets() {
    return Flux.fromIterable(datasets);
  }

  /*
   * (non-Javadoc)
   *
   * @see io.committed.invest.server.data.services.DatasetRegistryIntf#findById(java.lang.String)
   */
  @Override
  public Mono<Dataset> findById(final String id) {
    return getDatasets().filter(c -> c.getId().equalsIgnoreCase(id)).next();
  }
}
