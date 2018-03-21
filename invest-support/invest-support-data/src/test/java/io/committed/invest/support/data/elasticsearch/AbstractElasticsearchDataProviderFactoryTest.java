package io.committed.invest.support.data.elasticsearch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import io.committed.invest.extensions.data.providers.DataProvider;
import io.committed.invest.extensions.data.providers.DatabaseConstants;
import reactor.core.publisher.Mono;

public class AbstractElasticsearchDataProviderFactoryTest {

  private StubElasticserchDataProviderFactory dpf;


  @Before
  public void before() {
    dpf = new StubElasticserchDataProviderFactory();
  }

  @Test
  public void test() {
    assertThat(dpf.getDatabase()).isEqualTo(DatabaseConstants.ELASTICSEARCH);
    assertThat(dpf.getDataProvider()).isEqualTo(DataProvider.class);
    assertThat(dpf.getId()).isEqualTo("id");
  }

  @Test
  public void testWithDefaults() {
    final Map<String, Object> settings = Collections.emptyMap();
    dpf.build("dataset", "datasource", settings);

    assertThat(dpf.host).isEqualTo("localhost");
    assertThat(dpf.port).isEqualTo(9300);
    assertThat(dpf.esSettings.get("cluster.name")).isEqualTo("elasticsearch");

    assertThat(dpf.getIndexName(settings)).isEqualTo("index");
    assertThat(dpf.getTypeName(settings)).isEqualTo("type");
  }

  @Test
  public void testWithSettings() {

    final Map<String, Object> settings = new HashMap<>();

    settings.put(AbstractElasticsearchDataProviderFactory.SETTING_CLUSTER, "other_cluster");
    settings.put(AbstractElasticsearchDataProviderFactory.SETTING_HOST, "other_host");
    settings.put(AbstractElasticsearchDataProviderFactory.SETTING_PORT, 123);
    settings.put(AbstractElasticsearchDataProviderFactory.SETTING_INDEX, "other_index");
    settings.put(AbstractElasticsearchDataProviderFactory.SETTING_TYPE, "other_type");


    dpf.build("dataset", "datasource", settings);

    assertThat(dpf.host).isEqualTo("other_host");
    assertThat(dpf.port).isEqualTo(123);
    assertThat(dpf.esSettings.get("cluster.name")).isEqualTo("other_cluster");

    assertThat(dpf.getIndexName(settings)).isEqualTo("other_index");
    assertThat(dpf.getTypeName(settings)).isEqualTo("other_type");

  }


  public static class StubElasticserchDataProviderFactory
      extends AbstractElasticsearchDataProviderFactory<DataProvider> {

    private String host;
    private int port;
    private Settings esSettings;


    public StubElasticserchDataProviderFactory() {
      super("id", DataProvider.class, "index", "type");
    }

    @Override
    public Mono<DataProvider> build(final String dataset, final String datasource, final Map<String, Object> settings) {

      try {
        createElasticTemplate(settings);
      } catch (final Exception e) {
        fail(e.getMessage());
      }
      return Mono.just(mock(DataProvider.class));
    }

    @Override
    protected TransportClient createClient(final String host, final int port, final Settings esSettings)
        throws UnknownHostException {
      this.host = host;
      this.port = port;
      this.esSettings = esSettings;
      return mock(TransportClient.class);
    }


    @Override
    protected ElasticsearchTemplate createElasticTemplate(final Map<String, Object> settings)
        throws UnknownHostException {
      buildElasticClient(settings);
      return mock(ElasticsearchTemplate.class);
    }
  }
}
