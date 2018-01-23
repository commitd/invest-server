package io.committed.invest.server.core.config;

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
import io.committed.invest.extensions.registry.InvestUiExtensionRegistry;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class UiMerger implements WebFluxConfigurer {

  @Autowired(required = false)
  private InvestUiExtensionRegistry uiRegistry;

  @Autowired
  private UiUrlService urlService;

  @Bean
  public RouterFunction<ServerResponse> uiExtensionRoutes() {
    if (uiRegistry.isEmpty()) {
      log.warn("No UI extension points defined");
    }

    RouterFunction<ServerResponse> combined =
        RouterFunctions.route(RequestPredicates.path("/"), request -> ServerResponse.ok().build());


    for (final InvestUiExtension e : uiRegistry.getExtensions()) {

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
