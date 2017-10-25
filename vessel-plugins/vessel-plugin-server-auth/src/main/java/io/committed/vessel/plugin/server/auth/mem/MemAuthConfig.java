package io.committed.vessel.plugin.server.auth.mem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.MapUserDetailsRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import io.committed.vessel.plugin.server.auth.AbstractWithAuthSecurityConfig;

@Configuration
@Profile({ "auth-mem" })
public class MemAuthConfig extends AbstractWithAuthSecurityConfig {

  @Bean
  public MapUserDetailsRepository userDetailsRepository() {
    final UserDetails user = User.withUsername("user")
        .password("user")
        .roles("USER")
        .build();
    final UserDetails admin = User.withUsername("admin")
        .password("admin")
        .roles("ADMIN")
        .build();
    return new MapUserDetailsRepository(user, admin);
  }


}
