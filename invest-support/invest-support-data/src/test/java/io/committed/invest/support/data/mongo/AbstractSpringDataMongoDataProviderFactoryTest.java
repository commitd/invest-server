package io.committed.invest.support.data.mongo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import io.committed.invest.extensions.data.providers.DataProvider;
import reactor.core.publisher.Mono;

public class AbstractSpringDataMongoDataProviderFactoryTest {

  @Test
  public void testWithSettings() {
    final StubSpringDataMongoDataProviderFactory dpf = new StubSpringDataMongoDataProviderFactory();

    final Map<String, Object> settings = new HashMap<>();
    dpf.build("dataset", "datasource", settings);

    assertThat(dpf.connectionString).isEqualTo(AbstractMongoDataProviderFactory.DEFAULT_URI);
    assertThat(dpf.databaseName).isEqualTo("defaultDatabase");
  }

  @Test
  public void testDefaults() {
    final StubSpringDataMongoDataProviderFactory dpf = new StubSpringDataMongoDataProviderFactory();

    final Map<String, Object> settings = new HashMap<>();
    settings.put(AbstractMongoDataProviderFactory.SETTING_URI, "uri");

    settings.put(AbstractMongoDataProviderFactory.SETTING_DB, "other");
    dpf.build("dataset", "datasource", settings);

    assertThat(dpf.connectionString).isEqualTo("uri");
    assertThat(dpf.databaseName).isEqualTo("other");
  }


  public static class StubSpringDataMongoDataProviderFactory
      extends AbstractSpringDataMongoDataProviderFactory<DataProvider> {

    private String connectionString;
    private String databaseName;

    protected StubSpringDataMongoDataProviderFactory() {
      super("id", DataProvider.class, "defaultDatabase");
    }

    @Override
    public Mono<DataProvider> build(final String dataset, final String datasource, final Map<String, Object> settings) {
      buildMongoTemplate(settings);
      return null;
    }

    @Override
    protected ReactiveMongoTemplate createMongoTemplate(final String connectionString, final String databaseName) {
      this.connectionString = connectionString;
      this.databaseName = databaseName;
      return mock(ReactiveMongoTemplate.class);
    }
  }
}
