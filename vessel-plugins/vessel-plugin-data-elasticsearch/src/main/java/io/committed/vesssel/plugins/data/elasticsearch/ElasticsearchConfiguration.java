package io.committed.vesssel.plugins.data.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfiguration {

  @SuppressWarnings("resource")
  @Bean
  public Client elasticsearchClient() throws UnknownHostException {
    return new PreBuiltTransportClient(Settings.EMPTY)
        .addTransportAddress(
            new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
  }

}
