package io.committed.invest.server.data.services;


import java.util.Optional;
import io.committed.invest.core.exceptions.InvestConfigurationException;
import io.committed.invest.extensions.data.dataset.DataProviderSpecification;
import io.committed.invest.extensions.data.dataset.Dataset;
import io.committed.invest.extensions.data.dataset.DatasetRegistry;
import io.committed.invest.extensions.data.providers.DataProvider;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Create data provider(s) from a dataset or datasetRegistry.
 *
 */
public class DataProviderCreator {

  private final DataProviderFactoryRegistry dataProviderFactoryRegistry;

  public DataProviderCreator(final DataProviderFactoryRegistry dataProviderFactoryRegistry) {
    this.dataProviderFactoryRegistry = dataProviderFactoryRegistry;
  }

  public Flux<DataProvider> createProviders(final DatasetRegistry datasetRegistry) {
    return datasetRegistry.getDatasets().flatMap(this::createProviders);
  }

  public Flux<DataProvider> createProviders(final Dataset dataset) {
    return Flux.fromIterable(dataset.getProviders())
        .flatMap(s -> createProvider(dataset, s));
  }

  public Mono<DataProvider> createProvider(final Dataset dataset,
      final DataProviderSpecification spec) {
    final Mono<DataProvider> dp = dataProviderFactoryRegistry
        .build(dataset.getId(), spec)
        // The cast is because java complains about ? extend DataProvider
        .cast(DataProvider.class);

    // This seems cumbersome.. if its empty I want to pass back an error

    final Optional<DataProvider> optional = dp.blockOptional();

    if (optional.isPresent()) {
      return Mono.just(optional.get());
    }

    return Mono.error(new InvestConfigurationException(
        "DataProvider could not be created for Dataset '%s', DataSource %s with Factory '%s'. Could not be configured, missing factory? Bad settings?",
        dataset.getId(),
        spec.getDatasource(), spec.getFactory()));
  }

}
