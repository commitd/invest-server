package io.committed.invest.support.data.mongo;

import org.bson.Document;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import reactor.core.publisher.Mono;

public abstract class AbstractMongoRepositoryDataProvider<T, I, R extends ReactiveCrudRepository<T, I>>
    extends AbstractMongoDataProvider {

  private final String collectionName;
  private final MongoCollection<Document> collection;

  protected AbstractMongoRepositoryDataProvider(final String dataset, final String datasource,
      final MongoDatabase mongoDatabase, final String collectionName) {
    super(dataset, datasource, mongoDatabase);
    this.collectionName = collectionName;
    collection = mongoDatabase.getCollection(collectionName);
  }

  public String getCollectionName() {
    return collectionName;
  }

  public MongoCollection<Document> getCollection() {
    return collection;
  }

  public Mono<Long> count() {
    return Mono.from(collection.count());
  }

}
