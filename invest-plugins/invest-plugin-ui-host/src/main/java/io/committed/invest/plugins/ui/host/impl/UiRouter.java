package io.committed.invest.plugins.ui.host.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.committed.invest.core.services.UiUrlService;
import io.committed.invest.plugins.ui.host.data.InvestHostedUiExtensions;
import io.committed.invest.plugins.ui.host.data.PluginJson;

/** Mount the hosted UI extension into the HTTP server. */
@Configuration
@Slf4j
public class UiRouter {

  @Autowired(required = false)
  private InvestHostedUiExtensions extensions;

  @Autowired private UiUrlService urlService;

  @Bean
  public RouterFunction<ServerResponse> hostedUiRoutes() {

    RouterFunction<ServerResponse> combined =
        RouterFunctions.route(RequestPredicates.path("/"), request -> ServerResponse.ok().build());

    if (extensions == null || extensions.isEmpty()) {
      log.warn("No UI extension points defined");
    } else {

      for (final PluginJson p : extensions.getExtensions()) {
        final Resource resource = p.getResource();
        final String urlPath = urlService.getContextRelativePath(p);

        combined =
            combined.andNest(
                RequestPredicates.path(urlPath), RouterFunctions.resources("/**", resource));
      }
    }
    return RouterFunctions.nest(RequestPredicates.path(urlService.getContextPath()), combined);
  }
}
