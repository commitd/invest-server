package io.committed.invest.support.data.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import io.committed.invest.extensions.data.providers.AbstractDataProviderFactory;
import io.committed.invest.extensions.data.providers.DataProvider;
import io.committed.invest.extensions.data.providers.DatabaseConstants;

public abstract class AbstractElasticsearchDataProviderFactory<P extends DataProvider>
    extends AbstractDataProviderFactory<P> {

  public static final String SETTING_HOST = "host";
  public static final String SETTING_PORT = "port";
  public static final String SETTING_CLUSTER = "cluster";
  public static final String SETTING_TYPE = "type";
  public static final String SETTING_INDEX = "index";

  private static final String DEFAULT_HOST = "localhost";
  private static final int DEFAULT_PORT = 9300;
  private static final String DEFAULT_CLUSTER = "elasticsearch";

  private final String defaultIndexName;
  private final String defaultTypeName;

  protected AbstractElasticsearchDataProviderFactory(final String id, final Class<P> clazz,
      final String defaultIndexName, final String defaultTypeName) {
    super(id, clazz, DatabaseConstants.ELASTICSEARCH);
    this.defaultIndexName = defaultIndexName;
    this.defaultTypeName = defaultTypeName;
  }


  @SuppressWarnings({"resource", "squid:S00112"})
  protected Client buildElasticClient(final Map<String, Object> settings)
      throws UnknownHostException {

    final String host = (String) settings.getOrDefault(SETTING_HOST, DEFAULT_HOST);
    final int port = (int) settings.getOrDefault(SETTING_PORT, DEFAULT_PORT);
    final String cluster = (String) settings.getOrDefault(SETTING_CLUSTER, DEFAULT_CLUSTER);


    final Settings esSettings = Settings.builder()
        .put("cluster.name", cluster).build();

    return createClient(host, port, esSettings);
  }


  @SuppressWarnings("resource")
  protected TransportClient createClient(final String host, final int port, final Settings esSettings)
      throws UnknownHostException {
    return new PreBuiltTransportClient(esSettings)
        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
  }

  protected ElasticsearchTemplate createElasticTemplate(final Map<String, Object> settings)
      throws UnknownHostException {
    final Client client = buildElasticClient(settings);
    return new ElasticsearchTemplate(client);
  }

  protected String getIndexName(final Map<String, Object> settings) {
    return (String) settings.getOrDefault(SETTING_INDEX, defaultIndexName);

  }

  protected String getTypeName(final Map<String, Object> settings) {
    return (String) settings.getOrDefault(SETTING_TYPE, defaultTypeName);
  }
}
