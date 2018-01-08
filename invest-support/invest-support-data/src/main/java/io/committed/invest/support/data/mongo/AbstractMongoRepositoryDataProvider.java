package io.committed.invest.support.data.mongo;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public abstract class AbstractMongoRepositoryDataProvider<T, I, R extends ReactiveCrudRepository<T, I>>
    extends AbstractMongoDataProvider {

  private final R repository;

  protected AbstractMongoRepositoryDataProvider(final String dataset, final String datasource,
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

  // TODO: We could replicate the functions here... indeed implement the ReactiveCrudRepository with
  // NoBean...

}
