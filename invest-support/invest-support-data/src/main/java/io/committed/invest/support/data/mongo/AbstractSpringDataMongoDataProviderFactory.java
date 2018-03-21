package io.committed.invest.support.data.mongo;

import java.util.Map;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.support.ReactiveMongoRepositoryFactory;
import org.springframework.data.repository.core.support.ReactiveRepositoryFactorySupport;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import io.committed.invest.extensions.data.providers.AbstractDataProviderFactory;
import io.committed.invest.extensions.data.providers.DataProvider;
import io.committed.invest.extensions.data.providers.DatabaseConstants;

public abstract class AbstractSpringDataMongoDataProviderFactory<P extends DataProvider>
    extends AbstractDataProviderFactory<P> {

  private final String defaultDatabase;

  protected AbstractSpringDataMongoDataProviderFactory(final String id, final Class<P> clazz,
      final String defaultDatabase) {
    super(id, clazz, DatabaseConstants.MONGO);
    this.defaultDatabase = defaultDatabase;
  }

  protected ReactiveMongoTemplate buildMongoTemplate(final Map<String, Object> settings) {
    final String connectionString =
        (String) settings.getOrDefault(AbstractMongoDataProviderFactory.SETTING_URI,
            AbstractMongoDataProviderFactory.DEFAULT_URI);
    final String databaseName =
        (String) settings.getOrDefault(AbstractMongoDataProviderFactory.SETTING_DB, defaultDatabase);

    return createMongoTemplate(connectionString, databaseName);
  }

  protected ReactiveMongoTemplate createMongoTemplate(final String connectionString, final String databaseName) {
    final MongoClient mongoClient = MongoClients.create(connectionString);
    final SimpleReactiveMongoDatabaseFactory mongoDatabaseFactory =
        new SimpleReactiveMongoDatabaseFactory(mongoClient, databaseName);

    // If we need to control how the database collections are mapped, so we override the default
    // type mapping, which looks painful..

    // Finally create the factory support
    return new ReactiveMongoTemplate(mongoDatabaseFactory);
  }

  protected ReactiveRepositoryFactorySupport buildRepositoryFactory(
      final ReactiveMongoTemplate mongoOperations) {

    return new ReactiveMongoRepositoryFactory(mongoOperations);
  }



}
