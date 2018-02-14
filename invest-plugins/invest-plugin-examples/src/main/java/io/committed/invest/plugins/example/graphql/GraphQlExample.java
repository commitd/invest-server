package io.committed.invest.plugins.example.graphql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.committed.invest.extensions.InvestExtension;

@Configuration
public class GraphQlExample implements InvestExtension {

  @Bean
  public ThingGraphQlResolver thingService() {
    return new ThingGraphQlResolver();
  }
}
