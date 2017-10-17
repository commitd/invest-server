package io.committed.vessel.server.app.extensions.ui;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.committed.vessel.extensions.VesselUiExtension;

@Configuration
public class UiMerger {

  @Autowired(required = false)
  private final List<VesselUiExtension> extensions = Collections.emptyList();

  @Bean
  public RouterFunction<?> uiExtensionRoutes() {

    RouterFunction<ServerResponse> combined =
        RouterFunctions.route(RequestPredicates.path("/"), request -> ServerResponse.ok().build())
    // .and(RouterFunctions.route(RequestPredicates.path("/test"),
    // request -> ServerResponse.ok().syncBody("testing")))
    ;

    for (final VesselUiExtension e : extensions) {

      final String classPath = e.getStaticResourcePath();
      final String urlPath = String.format("/%s", e.getId());

      combined = combined
          .andNest(RequestPredicates.path(urlPath),
              RouterFunctions.resources("/**",
                  new ClassPathResource(classPath, e.getClass().getClassLoader())));
    }

    combined =
        combined.andRoute(RequestPredicates.all(), request -> ServerResponse.notFound().build());

    return RouterFunctions.nest(RequestPredicates.path("/ui"), combined);



  }

}
