package io.committed.vessel.plugins.example.api.status;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class StatusApiConfiguration {

  @Bean
  RouterFunction<?> statusRouter() {
    return RouterFunctions.route(RequestPredicates.GET("/api/status"),
        request -> {
          return ServerResponse.ok().syncBody("ok");
        });
  }
}
