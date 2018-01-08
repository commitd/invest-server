package io.committed.invest.server.app.config;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import io.committed.invest.core.services.UiUrlService;
import io.committed.invest.extensions.InvestUiExtension;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class UiMerger implements WebFluxConfigurer {

  @Autowired(required = false)
  private final List<InvestUiExtension> extensions = Collections.emptyList();

  @Autowired
  private UiUrlService urlService;

  @Bean
  public RouterFunction<?> uiExtensionRoutes() {

    if (extensions.isEmpty()) {
      log.warn("No UI extension points defined");
    }

    RouterFunction<ServerResponse> combined =
        RouterFunctions.route(RequestPredicates.path("/"), request -> ServerResponse.ok().build());

    for (final InvestUiExtension e : extensions) {

      final String classPath = e.getStaticResourcePath();
      if (!StringUtils.isEmpty(classPath)) {

        final String urlPath = urlService.getContextRelativePath(e);

        combined = combined.andNest(RequestPredicates.path(urlPath), RouterFunctions
            .resources("/**", new ClassPathResource(classPath, e.getClass().getClassLoader())));
      }
    }

    return RouterFunctions.nest(RequestPredicates.path(urlService.getContextPath()), combined);
  }


  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry.addMapping(urlService.getContextPath() + "/**").allowedHeaders("*")
        .allowedMethods("GET", "OPTIONS").allowedOrigins("*");
  }


}
