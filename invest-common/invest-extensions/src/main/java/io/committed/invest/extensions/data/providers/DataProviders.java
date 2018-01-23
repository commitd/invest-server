package io.committed.invest.extensions.data.providers;

import io.committed.invest.extensions.data.query.DataHints;
import reactor.core.publisher.Flux;


public interface DataProviders {

  Flux<DataProvider> findAll();

  // it is checked...
  <T> Flux<T> findAll(Class<T> providerClass);

  Flux<DataProvider> findForDataset(String datasetId);

  Flux<DataProvider> findForDataset(String datasetId, DataHints hints);

  <T> Flux<T> findForDataset(String datasetId, Class<T> providerClass);

  // It is checked...
  <T> Flux<T> findForDataset(String datasetId, Class<T> providerClass,
      DataHints hints);

}
