package io.committed.invest.support.data.elasticsearch;

import io.committed.invest.extensions.data.providers.AbstractDataProvider;
import io.committed.invest.extensions.data.providers.DatabaseConstants;

/**
 * Base for DataProvider using Elasticsearch.
 *
 * <p>This merely sets the database to ES.
 */
public abstract class AbstractElasticsearchDataProvider extends AbstractDataProvider {

  protected AbstractElasticsearchDataProvider(final String dataset, final String datasource) {
    super(dataset, datasource);
  }

  @Override
  public String getDatabase() {
    return DatabaseConstants.ELASTICSEARCH;
  }
}
