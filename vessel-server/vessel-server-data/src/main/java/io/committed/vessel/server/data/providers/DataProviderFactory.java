package io.committed.vessel.server.data.providers;

import java.util.Map;

import reactor.core.publisher.Mono;

public interface DataProviderFactory<T extends DataProvider> {

  String getId();

  Class<T> getDataProvider();

  String getDatabase();

  Mono<T> build(String dataset, String datasource, Map<String, Object> settings);

}
