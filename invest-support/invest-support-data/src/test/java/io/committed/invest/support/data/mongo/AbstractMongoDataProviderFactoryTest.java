package io.committed.invest.support.data.mongo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.committed.invest.extensions.data.providers.DataProvider;
import reactor.core.publisher.Mono;

public class AbstractMongoDataProviderFactoryTest {

  @Test
  public void testWithSettings() {
    final StubMongoDataProviderFactory dpf = new StubMongoDataProviderFactory();

    final Map<String, Object> settings = new HashMap<>();
    dpf.build("dataset", "datasource", settings);

    assertThat(dpf.connectionString).isEqualTo(AbstractMongoDataProviderFactory.DEFAULT_URI);
    assertThat(dpf.databaseName).isEqualTo("defaultDatabase");


    assertThat(dpf.getCollectionName(settings)).isEqualTo("defaultCollection");

  }

  @Test
  public void testDefaults() {
    final StubMongoDataProviderFactory dpf = new StubMongoDataProviderFactory();

    final Map<String, Object> settings = new HashMap<>();
    settings.put(AbstractMongoDataProviderFactory.SETTING_URI, "uri");
    settings.put(AbstractMongoDataProviderFactory.SETTING_DB, "other");
    settings.put(AbstractMongoDataProviderFactory.SETTING_COLLECTION, "otherCollection");


    dpf.build("dataset", "datasource", settings);

    assertThat(dpf.connectionString).isEqualTo("uri");
    assertThat(dpf.databaseName).isEqualTo("other");
    assertThat(dpf.getCollectionName(settings)).isEqualTo("otherCollection");

  }


  public static class StubMongoDataProviderFactory
      extends AbstractMongoDataProviderFactory<DataProvider> {


    private String connectionString;
    private String databaseName;


    protected StubMongoDataProviderFactory() {
      super("id", DataProvider.class, "defaultDatabase", "defaultCollection");
    }

    @Override
    public Mono<DataProvider> build(final String dataset, final String datasource, final Map<String, Object> settings) {
      buildMongoDatabase(settings);
      return null;
    }


    @Override
    protected MongoDatabase createDatabaseClient(final String connectionString, final String databaseName) {
      this.connectionString = connectionString;
      this.databaseName = databaseName;
      return mock(MongoDatabase.class);
    }


  }
}
