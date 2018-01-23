package io.committed.invest.support.data.mongo;

import org.bson.Document;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import io.committed.invest.extensions.data.providers.AbstractDataProvider;
import io.committed.invest.extensions.data.providers.DatabaseConstants;

public abstract class AbstractMongoDataProvider extends AbstractDataProvider {

  private final ReactiveMongoTemplate mongoTemplate;

  protected AbstractMongoDataProvider(final String dataset, final String datasource,
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


  // Spring doesn't have this yet
  protected AggregationExpression objectToArray(final String field) {
    return context -> new Document("$objectToArray", "$" + field);
  }
}
