package io.committed.vessel.plugin.server.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import io.committed.vessel.plugin.server.auth.constants.VesselRoles;
import io.committed.vessel.plugin.server.services.EnsureAdminUserExists;
import io.committed.vessel.plugin.server.services.UserAccountDetailsRepositoryService;
import io.committed.vessel.plugin.server.services.UserAccountRepository;
import io.committed.vessel.plugin.server.services.UserService;


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public abstract class AbstractWithAuthSecurityConfig {

  @Bean
  public SecurityWebFilterChain springWebFilterChain(final HttpSecurity http) {
    return http
        // we rely on method security
        .authorizeExchange()
        .pathMatchers("/actuator/**").hasRole(VesselRoles.ROLE_ADMINISTRATOR)
        .anyExchange().permitAll()
        .and()
        .build();
  }

  // TODO: Blocks forever with MongoReactive.. probably me (CF) not understanding the flux/mono
  @Bean
  public EnsureAdminUserExists ensureAdminUserExists(final UserService userService,
      final UserAccountRepository userAccounts) {
    return new EnsureAdminUserExists(userService, userAccounts);
  }

  @Bean
  public UserService userService(final UserAccountRepository userAccounts,
      final PasswordEncoder passwordEncoder) {
    return new UserService(userAccounts, passwordEncoder);
  }

  @Bean
  public UserAccountDetailsRepositoryService userAccountService(
      final UserAccountRepository repository) {
    return new UserAccountDetailsRepositoryService(repository);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
