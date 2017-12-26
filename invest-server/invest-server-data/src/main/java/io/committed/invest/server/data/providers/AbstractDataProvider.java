package io.committed.invest.server.data.providers;

public abstract class AbstractDataProvider implements DataProvider {

  private final String dataset;
  private final String datasource;

  protected AbstractDataProvider(final String dataset, final String datasource) {
    this.dataset = dataset;
    this.datasource = datasource;
  }

  @Override
  public String getDataset() {
    return dataset;
  }

  @Override
  public String getDatasource() {
    return datasource;
  }

}
