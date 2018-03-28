package io.committed.invest.extensions.data.providers;

import java.util.Map;

import reactor.core.publisher.Mono;

import io.committed.invest.extensions.data.dataset.DataProviderSpecification;

/**
 * A factory for creating DataProviders.
 *
 * <p>The id of the factory (getId) is referenced in the {@link DataProviderSpecification}.
 *
 * <p>The various fields of the DataProviderSpecification and the DataProviderFactory normally
 * define exactly the fields of the DataProvider.
 *
 * @param <T> the generic type
 */
public interface DataProviderFactory<T extends DataProvider> {

  /**
   * Gets the factory id.
   *
   * @return the id
   */
  String getId();

  /**
   * Gets the subinterface of DataProvider this will create.
   *
   * @return the data provider
   */
  Class<T> getDataProvider();

  /**
   * The database which this dataproviderfactory connects to.
   *
   * <p>See {@link DatabaseConstants} for a non-exhaustive list
   *
   * @return the database
   */
  String getDatabase();

  /**
   * Create a new instance of the data provider.
   *
   * @param dataset the dataset
   * @param datasource the datasource
   * @param settings the settings
   * @return the mono, return empty / error on failure (typically due to poor configuration)
   */
  Mono<T> build(String dataset, String datasource, Map<String, Object> settings);
}
