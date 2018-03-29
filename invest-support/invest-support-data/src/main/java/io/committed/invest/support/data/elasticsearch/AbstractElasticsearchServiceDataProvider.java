package io.committed.invest.support.data.elasticsearch;

/**
 * A base for DataProvider which is backed by Elasticsearch and uses the {@link
 * ElasticsearchSupportService}.
 *
 * <p>A very basic class, just use getService() to access the service.
 *
 * @param <E> the Java POJO representation of the type in ES
 * @param <R> the typed ElasticSearchSupportService
 */
public abstract class AbstractElasticsearchServiceDataProvider<
        E, R extends ElasticsearchSupportService<E>>
    extends AbstractElasticsearchDataProvider {

  private final R service;

  protected AbstractElasticsearchServiceDataProvider(
      final String dataset, final String datasource, final R service) {
    super(dataset, datasource);
    this.service = service;
  }

  protected R getService() {
    return service;
  }
}
