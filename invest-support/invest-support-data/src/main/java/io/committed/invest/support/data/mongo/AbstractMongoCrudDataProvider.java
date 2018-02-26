package io.committed.invest.support.data.mongo;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.reactivestreams.Publisher;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.committed.invest.extensions.data.providers.CrudDataProvider;
import reactor.core.publisher.Mono;

public abstract class AbstractMongoCrudDataProvider<R, T> extends AbstractMongoDataProvider
    implements CrudDataProvider<R, T> {

  private static final FindOneAndReplaceOptions UPSERT;

  static {
    UPSERT = new FindOneAndReplaceOptions();
    UPSERT.upsert(true);
  }

  protected AbstractMongoCrudDataProvider(final String dataset, final String datasource,
      final MongoDatabase mongoDatabase) {
    super(dataset, datasource, mongoDatabase);
  }

  protected Mono<Boolean> replace(final String collectionName, final Bson filter, final Document replacement) {
    final Publisher<Document> publisher = getCollection(collectionName)
        .findOneAndReplace(filter, replacement, UPSERT);
    // As an upsert this should always be true
    return Mono.just(publisher != null);
  }

  protected <S> Mono<Boolean> replace(final String collectionName, final Bson filter, final S replacement,
      final Class<S> clazz) {
    final Publisher<S> publisher = getCollection(collectionName, clazz)
        .findOneAndReplace(filter, replacement, UPSERT);
    // As an upsert this should always be true
    return Mono.just(publisher != null);
  }

  protected Mono<Boolean> update(final String collectionName, final Bson filter, final Bson update) {
    final Publisher<Document> publisher = getCollection(collectionName)
        .findOneAndUpdate(filter, update);
    return Mono.just(publisher != null);
  }

  protected Mono<Boolean> delete(final String collectionName, final Bson filter) {
    return Mono.justOrEmpty(filter)
        .flatMapMany(getCollection(collectionName)::deleteOne)
        .any(d -> d.getDeletedCount() > 0);
  }
}
