package io.committed.invest.support.data.jpa;

import io.committed.invest.server.data.providers.AbstractDataProvider;
import io.committed.invest.server.data.providers.DatabaseConstants;

public abstract class AbstractJpaDataProvider extends AbstractDataProvider {

  protected AbstractJpaDataProvider(final String dataset, final String datasource) {
    super(dataset, datasource);
  }

  @Override
  public String getDatabase() {
    return DatabaseConstants.SQL;
  }
}
