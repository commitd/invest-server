package io.committed.invest.support.data.mongo;

import java.util.Map;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.support.ReactiveMongoRepositoryFactory;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.core.support.ReactiveRepositoryFactorySupport;
import org.springframework.stereotype.Repository;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import io.committed.invest.extensions.data.providers.AbstractDataProviderFactory;
import io.committed.invest.extensions.data.providers.DataProvider;
import io.committed.invest.extensions.data.providers.DatabaseConstants;

/**
 * Base factory for data providers which will make use of Spring Data Mongo.
 *
 * <p>This will rely on Spring Data Mongo in order to provide the collectionName, set through the
 * {@link Document} annotation (or defaults to the entity class name).
 *
 * <p>You can use this to build {@link AbstractSpringDataMongoDataProvider} or {@link
 * AbstractSpringDataMongoRepositoryDataProvider}, depending if have a {@link Repository} or not.
 *
 * <p>Implementation will provide a build() function which will look like (if you have a repository
 * called MyMongoRepository):
 *
 * <pre>
 *  final ReactiveMongoTemplate template = buildMongoTemplate(settings);
 *  final ReactiveRepositoryFactorySupport factory = buildRepositoryFactory(template);
 *  MyMongoRepository repo = factory.getRepository(MyMongoRepository.class)
 *  return Mono.just(new MyMongoDataProvider(dataset, datasource, repo));
 * </pre>
 *
 * Note that MyMongoRepository should be annotated with {@link NoRepositoryBean} so that its created
 * by this factory, not by the Spring Data.
 *
 * @param <P> Data Provider type
 */
public abstract class AbstractSpringDataMongoDataProviderFactory<P extends DataProvider>
    extends AbstractDataProviderFactory<P> {

  private final String defaultDatabase;

  protected AbstractSpringDataMongoDataProviderFactory(
      final String id, final Class<P> clazz, final String defaultDatabase) {
    super(id, clazz, DatabaseConstants.MONGO);
    this.defaultDatabase = defaultDatabase;
  }

  protected ReactiveMongoTemplate buildMongoTemplate(final Map<String, Object> settings) {
    final String connectionString =
        (String)
            settings.getOrDefault(
                AbstractMongoDataProviderFactory.SETTING_URI,
                AbstractMongoDataProviderFactory.DEFAULT_URI);
    final String databaseName =
        (String)
            settings.getOrDefault(AbstractMongoDataProviderFactory.SETTING_DB, defaultDatabase);

    return createMongoTemplate(connectionString, databaseName);
  }

  protected ReactiveMongoTemplate createMongoTemplate(
      final String connectionString, final String databaseName) {
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
