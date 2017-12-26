package io.committed.invest.server.data.testing;

import io.committed.invest.server.data.providers.DataProvider;

public class FakeDataProvider implements DataProvider {

  public final static String DATASET = "test-dataset";
  public final static String DATASOURCE = "test-datasource";
  public final static String DATABASE = "test-db";

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
