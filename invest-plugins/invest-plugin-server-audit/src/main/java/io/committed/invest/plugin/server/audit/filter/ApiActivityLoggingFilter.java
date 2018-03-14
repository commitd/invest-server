package io.committed.invest.plugin.server.audit.filter;


import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import io.committed.invest.plugin.server.audit.services.AuditService;
import reactor.core.publisher.Mono;

/**
 * React web filter which captures api requests and redirect them to the audit logging service.
 *
 */
public class ApiActivityLoggingFilter implements WebFilter {

  private final AuditService auditService;

  public ApiActivityLoggingFilter(final AuditService auditService) {
    this.auditService = auditService;
  }

  @Override
  public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
    if (auditService.isLogging()) {
      return chain.filter(exchange).compose(call -> this.handle(exchange, call));
    } else {
      return chain.filter(exchange);
    }
  }


  private Mono<Void> handle(final ServerWebExchange exchange, final Mono<Void> call) {

    logRequest(exchange);

    return call.doOnSuccess(done -> this.logSuccess(exchange))
        .doOnError(reason -> this.logError(exchange, reason));

  }

  private void logRequest(final ServerWebExchange exchange) {
    final ServerHttpRequest request = exchange.getRequest();
    final Map<String, List<String>> requestMap = request.getQueryParams();
    auditService.audit(getUser(exchange), getAction("ApiReq", request), "", requestMap);
  }

  private void logSuccess(final ServerWebExchange exchange) {
    final ServerHttpResponse response = exchange.getResponse();
    final Map<String, Object> params = new HashMap<>(2);
    params.put("status", response.getStatusCode());
    auditService.audit(getUser(exchange), getAction("ApiRes", exchange.getRequest()), "", params);
  }

  private void logError(final ServerWebExchange exchange, final Throwable reason) {
    // TODO: Currently as per logSuccess but with a different action
    final ServerHttpResponse response = exchange.getResponse();
    final Map<String, Object> params = new HashMap<>(2);
    params.put("status", response.getStatusCode());
    auditService.audit(getUser(exchange), getAction("ApiError", exchange.getRequest()), "", params);
  }



  private String getAction(final String type, final ServerHttpRequest request) {
    return String.format("%s %s:%s", type, request.getMethod(), request.getURI());
  }

  private String getUser(final ServerWebExchange exchange) {
    final Mono<Principal> principal = exchange.getPrincipal();
    return principal.map(Principal::getName).or(Mono.just("anonymous")).block();
  }


}
