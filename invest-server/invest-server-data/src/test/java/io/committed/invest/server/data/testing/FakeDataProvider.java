package io.committed.invest.server.data.testing;

import io.committed.invest.extensions.data.providers.DataProvider;

public class FakeDataProvider implements DataProvider {

  public static final String DATASET = "test-dataset";
  public static final String DATASOURCE = "test-datasource";
  public static final String DATABASE = "test-db";

  @Override
  public String getProviderType() {
    return "TestProvider";
  }

  @Override
  public String getDatasource() {
    return DATASOURCE;
  }

  @Override
  public String getDataset() {
    return DATASET;
  }

  @Override
  public String getDatabase() {
    return DATABASE;
  }

  public boolean findExample() {
    return true;
  }
}
