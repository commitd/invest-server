package io.committed.invest.server.data.services;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import io.committed.invest.extensions.data.providers.DataProvider;
import io.committed.invest.extensions.data.providers.DataProviders;
import io.committed.invest.extensions.data.query.DataHints;
import reactor.core.publisher.Flux;

public class DefaultDatasetProviders implements DataProviders {

  private static final List<DataProvider> EMPTY_PROVIDER_LIST = Collections.emptyList();
  private final ImmutableListMultimap<String, DataProvider> providers;

  @Autowired
  public DefaultDatasetProviders(final List<DataProvider> providers) {
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

  /*
   * (non-Javadoc)
   *
   * @see io.committed.invest.server.data.services.DataProviderIntf#findAll()
   */
  @Override
  public Flux<DataProvider> findAll() {
    return Flux.fromIterable(providers.values());
  }

  // it is checked...
  /*
   * (non-Javadoc)
   *
   * @see io.committed.invest.server.data.services.DataProviderIntf#findAll(java.lang.Class)
   */
  @Override
  @SuppressWarnings("unchecked")
  public <T extends DataProvider> Flux<T> findAll(final Class<T> providerClass) {
    return (Flux<T>) findAll().filter(providerClass::isInstance);

  }

  /*
   * (non-Javadoc)
   *
   * @see io.committed.invest.server.data.services.DataProviderIntf#findForDataset(java.lang.String)
   */
  @Override
  public Flux<DataProvider> findForDataset(final String datasetId) {
    return findForDataset(datasetId, (DataHints) null);
  }

  /*
   * (non-Javadoc)
   *
   * @see io.committed.invest.server.data.services.DataProviderIntf#findForDataset(java.lang.String,
   * io.committed.invest.extensions.data.query.DataHints)
   */
  @Override
  public Flux<DataProvider> findForDataset(final String datasetId, final DataHints hints) {
    final List<DataProvider> datasetProviders = findAllForDataset(datasetId);
    final DataHints dh = getHints(hints);
    return dh.filter(datasetProviders);
  }

  /*
   * (non-Javadoc)
   *
   * @see io.committed.invest.server.data.services.DataProviderIntf#findForDataset(java.lang.String,
   * java.lang.Class)
   */
  @Override
  public <T extends DataProvider> Flux<T> findForDataset(final String datasetId, final Class<T> providerClass) {
    return findForDataset(datasetId, providerClass, null);
  }

  // It is checked...
  /*
   * (non-Javadoc)
   *
   * @see io.committed.invest.server.data.services.DataProviderIntf#findForDataset(java.lang.String,
   * java.lang.Class, io.committed.invest.extensions.data.query.DataHints)
   */
  @Override
  @SuppressWarnings("unchecked")
  public <T extends DataProvider> Flux<T> findForDataset(final String datasetId, final Class<T> providerClass,
      final DataHints hints) {

    return (Flux<T>) findForDataset(datasetId, hints).filter(providerClass::isInstance);
  }

  private DataHints getHints(final DataHints hints) {
    return hints != null ? hints : DataHints.DEFAULT;
  }

  @Override
  public <T extends DataProvider> Flux<T> find(final Class<T> providerClass, final DataHints hints) {
    final Flux<T> datasetProviders = findAll(providerClass);
    final DataHints dh = getHints(hints);
    return dh.filter(datasetProviders);
  }

}

