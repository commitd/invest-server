package io.committed.invest.plugins.example.api.status;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import io.committed.invest.extensions.InvestApiExtension;

@Configuration
public class StatusApiExtension implements InvestApiExtension {

  @Bean
  RouterFunction<ServerResponse> statusRouter() {
    return RouterFunctions.route(RequestPredicates.GET("/api/status"), request -> ServerResponse.ok().syncBody("ok"));
  }
}