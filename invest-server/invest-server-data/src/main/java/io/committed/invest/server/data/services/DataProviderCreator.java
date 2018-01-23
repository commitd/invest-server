package io.committed.invest.server.data.services;


import io.committed.invest.extensions.data.dataset.DataProviderSpecification;
import io.committed.invest.extensions.data.dataset.Dataset;
import io.committed.invest.extensions.data.providers.DataProvider;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DataProviderCreator {

  private final DataProviderFactoryRegistry dataProviderFactoryRegistry;

  public DataProviderCreator(final DataProviderFactoryRegistry dataProviderFactoryRegistry) {
    this.dataProviderFactoryRegistry = dataProviderFactoryRegistry;
  }

  public Flux<DataProvider> createProviders(final DatasetRegistry datasetRegistry) {
    return datasetRegistry.getDatasets().flatMap(this::createProviders);
  }

  public Flux<DataProvider> createProviders(final Dataset dataset) {
    return Flux.fromIterable(dataset.getProviders()).flatMap(s -> createProvider(dataset, s));
  }

  public Mono<? extends DataProvider> createProvider(final Dataset dataset,
      final DataProviderSpecification spec) {
    return dataProviderFactoryRegistry.build(dataset.getId(), spec);
  }

}
