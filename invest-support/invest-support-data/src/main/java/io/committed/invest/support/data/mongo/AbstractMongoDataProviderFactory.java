package io.committed.invest.support.data.mongo;

import java.util.Map;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.committed.invest.extensions.data.providers.AbstractDataProviderFactory;
import io.committed.invest.extensions.data.providers.DataProvider;
import io.committed.invest.extensions.data.providers.DatabaseConstants;

public abstract class AbstractMongoDataProviderFactory<P extends DataProvider>
    extends AbstractDataProviderFactory<P> {

  private final String defaultDatabaseName;
  private final String defaultCollectionName;

  protected AbstractMongoDataProviderFactory(final String id, final Class<P> clazz,
      final String defaultDatabaseName, final String defaultCollectionName) {
    super(id, clazz, DatabaseConstants.MONGO);
    this.defaultDatabaseName = defaultDatabaseName;
    this.defaultCollectionName = defaultCollectionName;
  }

  protected MongoDatabase buildMongoDatabase(final Map<String, Object> settings) {
    final String connectionString =
        (String) settings.getOrDefault("uri", "mongodb://localhost:27017/");
    final String databaseName = (String) settings.getOrDefault("db", defaultDatabaseName);


    final MongoClient mongoClient = MongoClients.create(connectionString);

    return mongoClient.getDatabase(databaseName);
  }

  protected String getCollectionName(final Map<String, Object> settings) {
    return (String) settings.getOrDefault("collection", defaultCollectionName);
  }

}
