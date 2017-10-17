package io.committed.vessel.plugins.example.graphql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQlExampleConfig {
  @Bean
  public ThingService thingService() {
    return new ThingService();
  }
}
