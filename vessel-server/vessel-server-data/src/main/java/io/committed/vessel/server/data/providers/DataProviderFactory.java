package io.committed.vessel.server.data.providers;

import java.util.Map;

import reactor.core.publisher.Mono;

public interface DataProviderFactory<T extends DataProvider> {

  String getId();

  Mono<T> build(String corpus, Map<String, Object> settings);

  Class<T> getDataProvider();

  String getDatasource();

}
