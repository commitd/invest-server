package io.committed.invest.support.data.mongo;

import java.util.Optional;
import org.bson.conversions.Bson;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.mongodb.reactivestreams.client.Success;
import io.committed.invest.extensions.data.providers.CrudDataProvider;
import reactor.core.publisher.Mono;

/**
 * CRUD implementation for a type which sits in isolation in its own collection.
 *
 * Though this seems attract it might hide certain integrity issues. For example if you delete an
 * author what should happen to their posts? If you change an author's name should you update all
 * the posts. If you have any cascading, the it's unlikely you have a simple isolated collection and
 * as such this class might not help you.
 *
 * 
 * @param <R>
 * @param <T>
 * @param <S>
 */
public abstract class AbstractMongoCrudCollectionDataProvider<R, T, S> extends AbstractMongoCollectionDataProvider<S>
    implements CrudDataProvider<R, T> {

  protected AbstractMongoCrudCollectionDataProvider(final String dataset, final String datasource,
      final MongoDatabase mongoDatabase, final String collectionName, final Class<S> pojoClazz) {
    super(dataset, datasource, mongoDatabase, collectionName, pojoClazz);
  }

  @Override
  public Mono<Boolean> delete(final R r) {
    final Optional<Bson> f = filter(r);
    return Mono.justOrEmpty(f)
        .flatMapMany(getCollection()::deleteOne)
        .any(d -> d.getDeletedCount() > 0);
  }


  @Override
  public Mono<Boolean> save(final T t) {
    final Optional<S> s = convert(t);
    return Mono.justOrEmpty(s)
        .flatMapMany(getCollection()::insertOne)
        .any(Success.SUCCESS::equals);
  }

  protected abstract Optional<Bson> filter(final R r);

  protected abstract Optional<S> convert(final T t);
}
