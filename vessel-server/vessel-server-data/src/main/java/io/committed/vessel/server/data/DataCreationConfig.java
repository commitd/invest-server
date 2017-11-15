package io.committed.vessel.server.data;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.committed.vessel.server.data.dataset.Dataset;
import io.committed.vessel.server.data.providers.DataProvider;
import io.committed.vessel.server.data.providers.DataProviderFactory;
import io.committed.vessel.server.data.services.DataProviderCreator;
import io.committed.vessel.server.data.services.DataProviderFactoryRegistry;
import io.committed.vessel.server.data.services.DatasetProviders;
import io.committed.vessel.server.data.services.DatasetRegistry;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DataCreationConfig {

  @Bean
  public DatasetProviders datasetProviders(
      @Autowired(required = false) final List<DataProvider> providers) {
    return new DatasetProviders(toSafeList(providers, "data providers"));
  }

  @Bean
  public DatasetRegistry datasetRegistry(
      @Autowired(required = false) final List<Dataset> datasets) {
    return new DatasetRegistry(toSafeList(datasets, "datasets"));
  }

  @Bean
  public DataProviderFactoryRegistry dataProviderFactoryRegistry(
      @Autowired(required = false) final List<DataProviderFactory<?>> factories) {
    return new DataProviderFactoryRegistry(toSafeList(factories, "data provider factories"));
  }

  // @Bean
  // @Order(Ordered.LOWEST_PRECEDENCE)
  // public DatasetDataProviderCreationService corpusDataProviderCreationService(
  // final DatasetRegistry datasetRegistry,
  // final DataProviderFactoryRegistry dataProviderFactoryRegistry) {
  // return new DatasetDataProviderCreationService(datasetRegistry, dataProviderFactoryRegistry);
  // }


  @Bean
  List<DataProvider> dataProviders(final DatasetRegistry datasetRegistry,
      final DataProviderFactoryRegistry dataProviderFactoryRegistry) {
    final DataProviderCreator creator =
        new DataProviderCreator(dataProviderFactoryRegistry);
    return creator.createProviders(datasetRegistry)
        .collect(Collectors.toList())
        .block();
  }

  private <T> List<T> toSafeList(final List<T> providers, final String name) {
    final List<T> list = providers == null ? Collections.emptyList() : providers;
    if (list.isEmpty()) {
      log.warn("No {} available, no data will be served", name);
    } else {
      log.warn("{} {} available", providers.size(), name);
    }
    return list;
  }
}
