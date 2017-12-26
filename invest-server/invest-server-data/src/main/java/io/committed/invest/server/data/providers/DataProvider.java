package io.committed.invest.server.data.providers;

public interface DataProvider {

  String getProviderType();

  String getDatasource();

  String getDatabase();

  String getDataset();

}
