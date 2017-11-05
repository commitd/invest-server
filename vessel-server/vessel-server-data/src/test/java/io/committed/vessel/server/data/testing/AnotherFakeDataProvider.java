package io.committed.vessel.server.data.testing;

import io.committed.vessel.server.data.providers.DataProvider;

public class AnotherFakeDataProvider implements DataProvider {

  @Override
  public String getProviderType() {
    return "AnotherFakeDataProvider";
  }

  @Override
  public String getDatasource() {
    return "test-dataset";
  }

  @Override
  public String getDataset() {
    return FakeDataProvider.DATASET;
  }

  public boolean findExample() {
    return false;
  }
}
