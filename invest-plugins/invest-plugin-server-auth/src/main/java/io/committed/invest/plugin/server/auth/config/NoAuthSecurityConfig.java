package io.committed.invest.plugin.server.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import io.committed.invest.core.auth.AuthenticationSettings;

@Profile({"auth-none"})
@Configuration
public class NoAuthSecurityConfig {


  @Bean
  public AuthenticationSettings authSettings() {
    return new AuthenticationSettings(true);
  }
}
