package io.committed.vessel.server.data.testing;

import io.committed.vessel.server.data.providers.DataProvider;

public class FakeDataProvider implements DataProvider {

  public final static String DATASET = "test-dataset";

  @Override
  public String getProviderType() {
    return "TestProvider";
  }

  @Override
  public String getDatasource() {
    return "test-dataset";
  }

  @Override
  public String getDataset() {
    return DATASET;
  }

  public boolean findExample() {
    return true;
  }
}
