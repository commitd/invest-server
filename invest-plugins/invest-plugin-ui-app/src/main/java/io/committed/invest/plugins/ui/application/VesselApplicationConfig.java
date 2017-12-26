package io.committed.invest.plugins.ui.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.server.WebFilter;

@Configuration
public class VesselApplicationConfig {

  @Bean
  @Order(value = Ordered.LOWEST_PRECEDENCE)
  public RouterFunction<?> applicationRootRoutes() {
    return RouterFunctions.resources("/**",
        new ClassPathResource("/ui/app/", this.getClass().getClassLoader()));
  }

  @Bean
  public WebFilter rootIndexHtmlWebFilter() {
    return new RootIndexHtmlWebFilter();
  }

  @Bean
  public GqlApplicationSettingService applicationSettingsService(
      final VesselUiApplicationSettings settings) {
    return new GqlApplicationSettingService(settings);
  }
}
