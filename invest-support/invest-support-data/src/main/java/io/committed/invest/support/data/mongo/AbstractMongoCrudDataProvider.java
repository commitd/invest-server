package io.committed.invest.support.data.mongo;

import java.util.Objects;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.reactivestreams.Publisher;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.committed.invest.extensions.data.providers.CrudDataProvider;
import reactor.core.publisher.Flux;
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

  protected boolean replace(final String collectionName, final Bson filter, final Document replacement) {
    final Publisher<Document> publisher = getCollection(collectionName)
        .findOneAndReplace(filter, replacement, UPSERT);
    return blockForBoolean(publisher);
  }

  protected <S> boolean replace(final String collectionName, final Bson filter, final S replacement,
      final Class<S> clazz) {
    final Publisher<S> publisher = getCollection(collectionName, clazz)
        .findOneAndReplace(filter, replacement, UPSERT);

    // As an upsert this should always be true
    return blockForBoolean(publisher);
  }

  protected boolean update(final String collectionName, final Bson filter, final Bson update) {
    final Publisher<Document> publisher = getCollection(collectionName)
        .findOneAndUpdate(filter, update);
    return blockForBoolean(publisher);
  }

  private <X> boolean blockForBoolean(final Publisher<X> publisher) {
    return Flux.from(publisher).next().map(Objects::nonNull).block();

  }

  protected boolean delete(final String collectionName, final Bson filter) {
    return Mono.justOrEmpty(filter)
        .flatMapMany(getCollection(collectionName)::deleteOne)
        .any(d -> d.getDeletedCount() > 0)
        .block();
  }
}
