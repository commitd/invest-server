package io.committed.invest.support.data.mongo;

import org.bson.Document;
import org.reactivestreams.Publisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.committed.invest.extensions.data.providers.AbstractDataProvider;
import io.committed.invest.extensions.data.providers.DataProvider;
import io.committed.invest.extensions.data.providers.DatabaseConstants;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Base class for implementation generic Mongo {@link DataProvider}s.
 *
 * Implementors will generally use {@link AbstractMongoCollectionDataProvider} rather than this
 * class.
 *
 */
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

  public <T> MongoCollection<T> getCollection(final String name, final Class<T> clazz) {
    return mongoDatabase.getCollection(name, clazz);
  }

  protected <S> Mono<S> toMono(final Publisher<S> publisher) {
    if (publisher == null) {
      return Mono.empty();
    }
    return Mono.from(publisher);
  }

  protected <S> Flux<S> toFlux(final Publisher<S> publisher) {
    if (publisher == null) {
      return Flux.empty();
    }
    return Flux.from(publisher);
  }
}
