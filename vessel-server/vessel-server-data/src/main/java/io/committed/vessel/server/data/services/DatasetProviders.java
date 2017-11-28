package io.committed.vessel.server.data.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;

import io.committed.vessel.server.data.providers.DataProvider;
import io.committed.vessel.server.data.query.DataHints;
import reactor.core.publisher.Flux;

public class DatasetProviders {

  private static final List<DataProvider> EMPTY_PROVIDER_LIST = Collections.emptyList();
  private final ImmutableListMultimap<String, DataProvider> providers;

  @Autowired
  public DatasetProviders(final List<DataProvider> providers) {
    this.providers = Multimaps.index(providers, DataProvider::getDataset);
  }

  private List<DataProvider> findAllForDataset(final String datasetId) {
    final ImmutableList<DataProvider> list = providers.get(datasetId);
    if (list == null || list.isEmpty()) {
      return EMPTY_PROVIDER_LIST;
    } else {
      return list;
    }
  }

  public Flux<DataProvider> findAll() {
    return Flux.fromIterable(providers.values());
  }

  // it is checked...
  @SuppressWarnings("unchecked")
  public <T> Flux<T> findAll(final Class<T> providerClass) {
    return (Flux<T>) findAll()
        .filter(providerClass::isInstance);

  }

  public Flux<DataProvider> findForDataset(final String datasetId) {
    return findForDataset(datasetId, (DataHints) null);
  }

  public Flux<DataProvider> findForDataset(final String datasetId, final DataHints hints) {
    final List<DataProvider> datasetProviders = findAllForDataset(datasetId);
    final DataHints dh = getHints(hints);

    return dh.apply(datasetProviders);
  }

  public <T> Flux<T> findForDataset(final String datasetId,
      final Class<T> providerClass) {
    return findForDataset(datasetId, providerClass, null);
  }

  // It is checked...
  @SuppressWarnings("unchecked")
  public <T> Flux<T> findForDataset(final String datasetId,
      final Class<T> providerClass, final DataHints hints) {

    return (Flux<T>) findForDataset(datasetId, hints)
        .filter(providerClass::isInstance);
  }

  private DataHints getHints(final DataHints hints) {
    return hints != null ? hints : DataHints.DEFAULT;
  }
}

