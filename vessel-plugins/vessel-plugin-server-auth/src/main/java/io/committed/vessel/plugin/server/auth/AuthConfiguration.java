package io.committed.vessel.plugin.server.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.userdetails.MapUserDetailsRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@ComponentScan(basePackageClasses = AuthConfiguration.class)
@EnableWebFluxSecurity
public class AuthConfiguration {

  @Bean
  public MapUserDetailsRepository userDetailsRepository() {
    final UserDetails user = User.withUsername("user")
        .password("user")
        .roles("USER")
        .build();
    return new MapUserDetailsRepository(user);
  }
}
