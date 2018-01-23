package io.committed.invest.server.core.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import io.committed.invest.core.services.UiUrlService;
import reactor.core.publisher.Mono;

@Component
public class AddIndexHtmlWebFilter implements WebFilter {

  private final UiUrlService service;

  public AddIndexHtmlWebFilter(final UiUrlService service) {
    this.service = service;
  }

  @Override
  public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
    final String path = exchange.getRequest().getURI().getPath();

    if (service.isPathForExtensionRoot(path)) {
      return chain.filter(exchange.mutate()
          .request(exchange.getRequest().mutate().path(path + "/index.html").build()).build());
    }

    return chain.filter(exchange);
  }
}
