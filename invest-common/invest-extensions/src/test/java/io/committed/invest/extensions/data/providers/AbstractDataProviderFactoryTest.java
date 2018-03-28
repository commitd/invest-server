package io.committed.invest.extensions.data.providers;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import reactor.core.publisher.Mono;

public class AbstractDataProviderFactoryTest {

  @Test
  public void test() {
    final StubDPF dpf = new StubDPF();

    assertEquals("id", dpf.getId());
    assertEquals(DataProvider.class, dpf.getDataProvider());
    assertEquals("database", dpf.getDatabase());
  }

  public class StubDPF extends AbstractDataProviderFactory<DataProvider> {

    protected StubDPF() {
      super("id", DataProvider.class, "database");
    }

    @Override
    public Mono<DataProvider> build(
        final String dataset, final String datasource, final Map<String, Object> settings) {
      return null;
    }
  }
}
