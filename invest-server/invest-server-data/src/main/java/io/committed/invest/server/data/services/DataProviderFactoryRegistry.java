package io.committed.invest.server.data.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.committed.invest.extensions.data.dataset.DataProviderSpecification;
import io.committed.invest.extensions.data.providers.DataProvider;
import io.committed.invest.extensions.data.providers.DataProviderFactory;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class DataProviderFactoryRegistry {

  private final List<DataProviderFactory<DataProvider>> factories;

  @Autowired
  public DataProviderFactoryRegistry(final List<DataProviderFactory<DataProvider>> factories) {
    this.factories = factories;
  }

  public Flux<DataProviderFactory<DataProvider>> findFactories(final String id) {
    return Flux.fromIterable(factories).filter(f -> f.getId().equalsIgnoreCase(id));
  }

  // This is checked in the flux
  @SuppressWarnings("unchecked")
  public <P extends DataProvider> Flux<DataProviderFactory<P>> findFactories(final String id,
      final Class<P> clazz) {
    return findFactories(id).filter(f -> clazz.isAssignableFrom(f.getDataProvider()))
        .map(f -> (DataProviderFactory<P>) f);
  }


  public Mono<DataProvider> build(final String dataset,
      final DataProviderSpecification spec) {
    final Map<String, Object> safeSettings =
        spec.getSettings() == null ? Collections.emptyMap() : spec.getSettings();

    final String factoryId = spec.getFactory();

    return findFactories(factoryId).flatMap(f -> {
      try {
        return f.build(dataset, spec.getDatasource(), safeSettings);
      } catch (final Exception e) {
        log.warn("Unable to create data provider due to error", e);
        return Mono.empty();
      }
    })
        // Grab the first non empty
        .next()
        .map(DataProvider.class::cast);
  }
}

