package io.committed.invest.extensions.data.providers;

/**
 * A Data Provider
 *
 * <p>These are created by Invest from Data Provider Specifications using DataProviderFactories.
 */
public interface DataProvider {

  /**
   * Gets the provider type.
   *
   * @return the provider type
   */
  String getProviderType();

  /**
   * Gets the datasource.
   *
   * @return the datasource
   */
  String getDatasource();

  /**
   * Gets the database.
   *
   * @return the database
   */
  String getDatabase();

  /**
   * Gets the dataset.
   *
   * @return the dataset
   */
  String getDataset();
}
