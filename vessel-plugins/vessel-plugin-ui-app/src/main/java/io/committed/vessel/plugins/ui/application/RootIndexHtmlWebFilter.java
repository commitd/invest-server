package io.committed.vessel.plugins.ui.application;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

public class RootIndexHtmlWebFilter implements WebFilter {

  @Override
  public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
    if (exchange.getRequest().getURI().getPath().equals("/")) {
      return chain.filter(exchange.mutate()
          .request(exchange.getRequest().mutate().path("/index.html").build()).build());
    }

    return chain.filter(exchange);
  }
}
