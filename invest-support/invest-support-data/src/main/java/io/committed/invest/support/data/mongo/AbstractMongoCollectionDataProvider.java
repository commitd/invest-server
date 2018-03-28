package io.committed.invest.support.data.mongo;

import java.util.List;
import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * A base for DataProvider which access a single Mongo Collection .
 *
 * @param <T> the POJO representation of the documents in the collection
 */
public abstract class AbstractMongoCollectionDataProvider<T> extends AbstractMongoDataProvider {

  private final String collectionName;
  private final MongoCollection<T> mongoCollection;

  protected AbstractMongoCollectionDataProvider(final String dataset, final String datasource,
      final MongoDatabase mongoDatabase, final String collectionName, final Class<T> pojoClazz) {
    super(dataset, datasource, mongoDatabase);
    this.collectionName = collectionName;
    this.mongoCollection = mongoDatabase.getCollection(collectionName, pojoClazz);
  }

  public String getCollectionName() {
    return collectionName;
  }

  public MongoCollection<T> getCollection() {
    return mongoCollection;
  }

  protected Mono<T> findByField(final String field, final String value) {
    return toMono(getCollection().find(Filters.eq(field, value)).first());
  }

  protected Flux<T> findAllByField(final String field, final String value) {
    return toFlux(getCollection().find(Filters.eq(field, value)));
  }

  protected Flux<T> findAll(final int offset, final int size) {
    return toFlux(getCollection().find()
        .skip(offset)
        .limit(size));
  }

  protected <S> Flux<S> aggregate(final List<Bson> pipeline, final Class<S> outputClass) {
    return toFlux(getCollection().aggregate(pipeline, outputClass));
  }

  protected Mono<Long> count() {
    return toMono(getCollection().count());
  }

}
