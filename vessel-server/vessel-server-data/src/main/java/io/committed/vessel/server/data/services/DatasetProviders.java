package io.committed.vessel.server.data.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;

import io.committed.vessel.server.data.providers.DataProvider;
import reactor.core.publisher.Flux;

public class DatasetProviders {

  public static final List<DataProvider> EMPTY_PROVIDER_LIST = Collections.emptyList();
  private final ImmutableListMultimap<String, DataProvider> providers;

  @Autowired
  public DatasetProviders(final List<DataProvider> providers) {
    this.providers = Multimaps.index(providers, DataProvider::getDataset);
  }

  public Flux<DataProvider> findForDataset(final String datasetId) {
    final ImmutableList<DataProvider> list = providers.get(datasetId);
    if (list == null || list.isEmpty()) {
      return Flux.empty();
    } else {
      return Flux.fromIterable(list);
    }
  }

  // It is checked...
  @SuppressWarnings("unchecked")
  public <T> Flux<T> findForDataset(final String datasetId,
      final Class<T> providerClass) {
    return (Flux<T>) findForDataset(datasetId)
        .filter(providerClass::isInstance);

  }

}

