package io.committed.invest.server.data;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.committed.invest.core.exceptions.InvestConfigurationException;
import io.committed.invest.extensions.data.dataset.Dataset;
import io.committed.invest.extensions.data.dataset.DatasetRegistry;
import io.committed.invest.extensions.data.providers.DataProvider;
import io.committed.invest.extensions.data.providers.DataProviderFactory;
import io.committed.invest.extensions.data.providers.DataProviders;
import io.committed.invest.server.data.services.DataProviderCreator;
import io.committed.invest.server.data.services.DataProviderFactoryRegistry;
import io.committed.invest.server.data.services.DefaultDatasetProviders;
import io.committed.invest.server.data.services.DefaultDatasetRegistry;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Exceptions;

@Configuration
@Slf4j
public class DataCreationConfig {

  @Bean
  public DataProviders datasetProviders(
      @Autowired(required = false) final List<DataProvider> providers) {
    return new DefaultDatasetProviders(toSafeList(providers, "data providers"));
  }

  @Bean
  public DatasetRegistry datasetRegistry(
      @Autowired(required = false) final List<Dataset> datasets) {
    return new DefaultDatasetRegistry(toSafeList(datasets, "datasets"));
  }

  @Bean
  // NOTE: Do not remove the ? extends here... otherwise Spring does not wire any beans in!
  public DataProviderFactoryRegistry dataProviderFactoryRegistry(
      @Autowired(required = false) final List<DataProviderFactory<? extends DataProvider>> factories) {
    return new DataProviderFactoryRegistry(toSafeList(factories, "data provider factories"));
  }

  @Bean
  public List<DataProvider> dataProviders(final DatasetRegistry datasetRegistry,
      final DataProviderFactoryRegistry dataProviderFactoryRegistry) {
    final DataProviderCreator creator = new DataProviderCreator(dataProviderFactoryRegistry);
    return creator.createProviders(datasetRegistry)
        .doOnError(e -> {
          // Not this doesn't handle the exception, it just prints a log message nicely!
          if (Exceptions.unwrap(e) instanceof InvestConfigurationException) {
            final InvestConfigurationException t = (InvestConfigurationException) Exceptions.unwrap(e);
            log.error(t.getMessage());
          } else {
            log.error("Failed to create", e);
          }
        })
        .collect(Collectors.toList())
        .block();

  }

  private <T> List<T> toSafeList(final List<T> providers, final String name) {
    final List<T> list = providers == null ? Collections.emptyList() : providers;
    // Technically this provides null check is not required, but Sonar complains.
    if (providers == null || list.isEmpty()) {
      log.warn("No {} available, no data will be served", name);
    } else {
      log.info("{} {} available", providers.size(), name);
    }
    return list;
  }
}
