package io.committed.invest.extensions.data.providers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AbstractDataProviderTest {

  @Test
  public void test() {
    final StubDP dp = new StubDP("ds", "source");

    assertEquals("ds", dp.getDataset());
    assertEquals("source", dp.getDatasource());
  }

  public class StubDP extends AbstractDataProvider {

    protected StubDP(final String dataset, final String datasource) {
      super(dataset, datasource);
    }

    @Override
    public String getProviderType() {
      return "providerType";
    }

    @Override
    public String getDatabase() {
      return "db";
    }
  }
}
