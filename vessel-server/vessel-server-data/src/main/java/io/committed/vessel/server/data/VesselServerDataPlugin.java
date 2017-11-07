package io.committed.vessel.server.data;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.committed.vessel.extensions.VesselServiceExtension;
import io.committed.vessel.server.data.dataset.Dataset;
import io.committed.vessel.server.data.providers.DataProvider;
import io.committed.vessel.server.data.providers.DataProviderFactory;
import io.committed.vessel.server.data.services.DataProviderFactoryRegistry;
import io.committed.vessel.server.data.services.DatasetDataProviderCreationService;
import io.committed.vessel.server.data.services.DatasetProviders;
import io.committed.vessel.server.data.services.DatasetRegistry;

public class VesselServerDataPlugin implements VesselServiceExtension {

  @Override
  public Class<?> getConfiguration() {
    return PluginConfiguration.class;
  }


  @Configuration
  @ComponentScan(basePackageClasses = PluginConfiguration.class)
  public static class PluginConfiguration {


    @Bean
    public DatasetProviders corpusProviders(
        @Autowired(required = false) final List<DataProvider> providers) {
      return new DatasetProviders(providers == null ? Collections.emptyList() : providers);
    }

    @Bean
    public DatasetRegistry corpusRegistry(
        @Autowired(required = false) final List<Dataset> corpora) {
      return new DatasetRegistry(corpora == null ? Collections.emptyList() : corpora);
    }

    @Bean
    public DataProviderFactoryRegistry dataProviderFactoryRegistry(
        @Autowired(required = false) final List<DataProviderFactory<?>> factories) {
      return new DataProviderFactoryRegistry(
          factories == null ? Collections.emptyList() : factories);
    }

    @Bean
    public DatasetDataProviderCreationService corpusDataProviderCreationService(
        final DatasetRegistry corpusRegistry,
        final DataProviderFactoryRegistry dataProviderFactoryRegistry) {
      return new DatasetDataProviderCreationService(corpusRegistry, dataProviderFactoryRegistry);
    }

  }
}
