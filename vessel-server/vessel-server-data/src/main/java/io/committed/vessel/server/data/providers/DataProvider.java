package io.committed.vessel.server.data.providers;

public interface DataProvider {

  String getProviderType();

  String getDatasource();

  String getDatabase();

  String getDataset();

}
