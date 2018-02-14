package io.committed.invest.server.data.testing;

import io.committed.invest.extensions.data.providers.DataProvider;

public class AnotherFakeDataProvider implements DataProvider {

  @Override
  public String getProviderType() {
    return "AnotherFakeDataProvider";
  }

  @Override
  public String getDatasource() {
    return FakeDataProvider.DATASOURCE;
  }

  @Override
  public String getDataset() {
    return FakeDataProvider.DATASET;
  }

  @Override
  public String getDatabase() {
    return FakeDataProvider.DATABASE;
  }

  public boolean findExample() {
    return false;
  }
}
