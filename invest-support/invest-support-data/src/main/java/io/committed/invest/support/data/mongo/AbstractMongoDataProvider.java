package io.committed.invest.support.data.mongo;

import org.bson.Document;
import org.reactivestreams.Publisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.committed.invest.extensions.data.providers.AbstractDataProvider;
import io.committed.invest.extensions.data.providers.DatabaseConstants;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class AbstractMongoDataProvider extends AbstractDataProvider {

  private final MongoDatabase mongoDatabase;

  protected AbstractMongoDataProvider(final String dataset, final String datasource,
      final MongoDatabase mongoDatabase) {
    super(dataset, datasource);
    this.mongoDatabase = mongoDatabase;
  }

  @Override
  public String getDatabase() {
    return DatabaseConstants.MONGO;
  }

  public MongoDatabase getMongoDatabase() {
    return mongoDatabase;
  }

  public MongoCollection<Document> getCollection(final String name) {
    return mongoDatabase.getCollection(name);
  }

  protected <S> Mono<S> toMono(final Publisher<S> publisher) {
    return Mono.from(publisher);
  }

  protected <S> Flux<S> toFlux(final Publisher<S> publisher) {
    return Flux.from(publisher);
  }
}
