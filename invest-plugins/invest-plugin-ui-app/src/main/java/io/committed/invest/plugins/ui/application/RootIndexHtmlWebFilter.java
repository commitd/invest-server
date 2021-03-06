package io.committed.invest.plugins.ui.application;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

/**
 * Redirects from the root location to the actual hosted location of the application UI files.
 *
 * <p>Obvioulsy we want to simplify the URL the user need to remember (http://server/) but hosting
 * static files on that becomes complex as it clashes with apis, ui and in some cases development
 * support (eg webpack live reload).
 */
public class RootIndexHtmlWebFilter implements WebFilter {

  @Override
  public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
    final String path = exchange.getRequest().getURI().getPath();
    if (path.equals("/") || path.startsWith("/index")) {

      final ServerHttpResponse response = exchange.getResponse();
      response.setStatusCode(HttpStatus.SEE_OTHER);
      response.getHeaders().add(HttpHeaders.LOCATION, "/ui/app/");
      return Mono.just(response).then();
    } else {
      return chain.filter(exchange);
    }
  }
}
