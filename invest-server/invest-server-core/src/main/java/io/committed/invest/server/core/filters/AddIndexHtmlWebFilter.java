package io.committed.invest.server.core.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

import io.committed.invest.core.services.UiUrlService;

/** Redirect from the '/' to the outer application index.html (which is not hosted on /) */
@Component
public class AddIndexHtmlWebFilter implements WebFilter {

  private final UiUrlService service;

  public AddIndexHtmlWebFilter(final UiUrlService service) {
    this.service = service;
  }

  @Override
  public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
    String path = exchange.getRequest().getURI().getPath();

    if (!path.endsWith("/")) {
      path = path + "/";
    }

    if (service.isPathForExtensionRoot(path)) {
      return chain.filter(
          exchange
              .mutate()
              .request(exchange.getRequest().mutate().path(path + "index.html").build())
              .build());
    }

    return chain.filter(exchange);
  }
}
