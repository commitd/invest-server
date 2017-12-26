package io.committed.invest.plugins.ui.libs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class UiLibsConfig {

  @Bean
  public RouterFunction<?> root() {
    return RouterFunctions.resources("/static/libs/**",
        new ClassPathResource("/ui/libs/", this.getClass().getClassLoader()));
  }

}
