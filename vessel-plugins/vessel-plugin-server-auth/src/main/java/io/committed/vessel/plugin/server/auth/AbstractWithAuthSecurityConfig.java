package io.committed.vessel.plugin.server.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.HttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public abstract class AbstractWithAuthSecurityConfig {

  @Bean
  public SecurityWebFilterChain springWebFilterChain(final HttpSecurity http) {
    return http
        // we rely on method security
        .authorizeExchange()
        .anyExchange().permitAll()
        .and()
        .build();
  }
}
