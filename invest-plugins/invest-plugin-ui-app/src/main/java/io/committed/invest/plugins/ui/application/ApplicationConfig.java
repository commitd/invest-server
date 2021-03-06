package io.committed.invest.plugins.ui.application;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebFilter;

import io.committed.invest.core.auth.AuthenticationSettings;

/** Spring configuration for the main application. */
@Configuration
public class ApplicationConfig {

  @Autowired private ApplicationSettings settings;

  @Bean
  @Order(value = Ordered.LOWEST_PRECEDENCE)
  public RouterFunction<ServerResponse> applicationRootRoutes() {
    Resource resource;
    if (settings.isHostedFromFileSystem()) {
      resource = new PathResource(new File(settings.getDirectory()).getAbsoluteFile().toPath());
    } else {
      // Fallback to classpath
      resource = new ClassPathResource("/ui/app/", this.getClass().getClassLoader());
    }

    return RouterFunctions.resources("/ui/app/**", resource);
  }

  @Bean
  public WebFilter rootIndexHtmlWebFilter() {
    return new RootIndexHtmlWebFilter();
  }

  @Bean
  public UiApplicationSettingGraphQlResolver applicationSettingsService(
      final UiApplicationSettings settings) {
    return new UiApplicationSettingGraphQlResolver(settings);
  }

  @ConditionalOnMissingBean(AuthenticationSettings.class)
  @Bean
  public AuthenticationSettings defaultAuthSettings() {
    return new AuthenticationSettings(false);
  }
}
