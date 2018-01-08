package io.committed.invest.support.data.elasticsearch;

public abstract class AbstractElasticsearchServiceDataProvider<R extends AbstractEsService>
    extends AbstractElasticsearchDataProvider {

  private final R service;

  protected AbstractElasticsearchServiceDataProvider(final String dataset, final String datasource,
      final R service) {
    super(dataset, datasource);
    this.service = service;
  }

  protected R getService() {
    return service;
  }


}
