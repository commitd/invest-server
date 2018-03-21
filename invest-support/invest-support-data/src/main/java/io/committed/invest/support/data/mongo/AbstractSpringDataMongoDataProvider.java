package io.committed.invest.support.data.mongo;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import io.committed.invest.extensions.data.providers.AbstractDataProvider;
import io.committed.invest.extensions.data.providers.DatabaseConstants;

public abstract class AbstractSpringDataMongoDataProvider extends AbstractDataProvider {

  private final ReactiveMongoTemplate mongoTemplate;

  protected AbstractSpringDataMongoDataProvider(final String dataset, final String datasource,
      final ReactiveMongoTemplate mongoTemplate) {
    super(dataset, datasource);
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public String getDatabase() {
    return DatabaseConstants.MONGO;
  }

  protected ReactiveMongoTemplate getTemplate() {
    return mongoTemplate;
  }

}
