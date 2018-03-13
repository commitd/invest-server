package io.committed.invest.extensions.data.providers;

import java.util.Map;
import io.committed.invest.extensions.data.dataset.DataProviderSpecification;
import reactor.core.publisher.Mono;

/**
 * A factory for creating DataProviders.
 *
 * The id of the factory (getId) is referenced in the {@link DataProviderSpecification}.
 *
 * The various fields of the DataProviderSpecification and the DataProviderFactory normally define
 * exactly the fields of the DataProvider.
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
   * See {@link DatabaseConstants} for a non-exhaustive list
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
