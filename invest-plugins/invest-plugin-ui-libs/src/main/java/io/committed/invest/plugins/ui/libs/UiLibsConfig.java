package io.committed.invest.plugins.ui.libs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UiLibsConfig {

  @Bean
  public RouterFunction<ServerResponse> root() {
    return RouterFunctions.resources("/ui/libs/**",
        new ClassPathResource("/ui/libs/", this.getClass().getClassLoader()));
  }

}
