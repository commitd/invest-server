package io.committed.invest.support.data.jpa;

import io.committed.invest.extensions.data.providers.AbstractDataProvider;
import io.committed.invest.extensions.data.providers.DatabaseConstants;

/**
 * Base class for building Jpa DataProviders.
 */
public abstract class AbstractJpaDataProvider extends AbstractDataProvider {

  protected AbstractJpaDataProvider(final String dataset, final String datasource) {
    super(dataset, datasource);
  }

  @Override
  public String getDatabase() {
    return DatabaseConstants.SQL;
  }
}
