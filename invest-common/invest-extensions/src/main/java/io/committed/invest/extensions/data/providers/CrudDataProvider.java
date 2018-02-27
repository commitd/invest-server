package io.committed.invest.extensions.data.providers;

import reactor.core.publisher.Mono;

public interface CrudDataProvider<R, T> extends DataProvider {

  Mono<Boolean> delete(R reference);

  Mono<Boolean> save(T item);
}
