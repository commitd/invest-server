package io.committed.invest.support.data.mongo;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public abstract class AbstractSpringDataMongoRepositoryDataProvider<T, I, R extends ReactiveCrudRepository<T, I>>
    extends AbstractSpringDataMongoDataProvider {

  private final R repository;

  protected AbstractSpringDataMongoRepositoryDataProvider(final String dataset, final String datasource,
      final ReactiveMongoTemplate mongoTemplate, final R repository) {
    super(dataset, datasource, mongoTemplate);
    this.repository = repository;
  }

  protected R getRepository() {
    return repository;
  }


  public Mono<Long> count() {
    return repository.count();
  }


}
