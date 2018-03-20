package io.committed.invest.extensions.data.providers;

import io.committed.invest.extensions.data.query.DataHints;
import reactor.core.publisher.Flux;


/**
 * A registry of data providers available in the application.
 *
 * If you want to access a dataprovider then it is likely you should autowire this class into your
 * service. It will then support you in find the right dataprovider for the dataset you are trying
 * to access.
 *
 * The methods within this class return a flux, which may be empty or have multiple results. When
 * empty the request can not be satistied and the application should return this (returning no
 * results, a configuration error, or a default value).
 *
 * When the multiple data providers are returned it is up the application to determine the best
 * action. Some will only want the first data provider so will return
 *
 * <pre>
 * flux.next().map(dataProvider -&gt; doSomething(dataProvider)).
 * </pre>
 *
 * Others will wish to combine the answers from all data providers for example with flatMap:
 *
 * <pre>
 * flux.flatMap(dataProvider -&gt; doSomething(dataProvider))
 * </pre>
 *
 * {@link DataHints} can be used to help specify which data providers are designed. They acts as a
 * very basic filter to select data provider based on datasource or database.
 *
 */
public interface DataProviders {

  // TODO: We could default a lot of these (see DefaultDataProviders for the implementation)

  /**
   * Get all data providers.
   *
   * @return the flux
   */
  Flux<DataProvider> findAll();

  /**
   * Get all data providesr which are subclasses of a specific class.
   *
   * @param <T> the generic type
   * @param providerClass the provider class
   * @return the flux
   */
  <T extends DataProvider> Flux<T> findAll(Class<T> providerClass);

  /**
   * Get all dataprovidesr for a specific dataset.
   *
   * @param datasetId the dataset id
   * @return the flux
   */
  Flux<DataProvider> findForDataset(String datasetId);

  /**
   * Get all dataproviders for a dataset, filtered by hints.
   *
   * @param datasetId the dataset id
   * @param hints the hints
   * @return the flux
   */
  Flux<DataProvider> findForDataset(String datasetId, DataHints hints);

  /**
   * Get data provider of a specific class for a specified dataset.
   *
   * @param <T> the generic type
   * @param datasetId the dataset id
   * @param providerClass the provider class
   * @return the flux
   */
  <T extends DataProvider> Flux<T> findForDataset(String datasetId, Class<T> providerClass);

  /**
   * Get data providers of a specific class for a specified dataset, filtered by hints.
   *
   * @param <T> the generic type
   * @param datasetId the dataset id
   * @param providerClass the provider class
   * @param hints the hints
   * @return the flux
   */
  <T extends DataProvider> Flux<T> findForDataset(String datasetId, Class<T> providerClass,
      DataHints hints);

  /**
   * Get alls data providers of a specific class for a specified dataset.
   *
   * @param <T> the generic type
   * @param providerClass the provider class
   * @param hints the hints
   * @return the flux
   */
  <T extends DataProvider> Flux<T> find(Class<T> providerClass, DataHints hints);

}
