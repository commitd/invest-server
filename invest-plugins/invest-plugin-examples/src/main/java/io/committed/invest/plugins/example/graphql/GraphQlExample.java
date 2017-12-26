package io.committed.invest.plugins.example.graphql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.committed.invest.extensions.VesselExtension;

@Configuration
public class GraphQlExample implements VesselExtension {

  @Bean
  public ThingService thingService() {
    return new ThingService();
  }
}
