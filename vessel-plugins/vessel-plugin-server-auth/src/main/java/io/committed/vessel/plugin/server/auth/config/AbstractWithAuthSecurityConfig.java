package io.committed.vessel.plugin.server.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.support.ReactiveMongoRepositoryFactory;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import io.committed.vessel.plugin.server.auth.constants.VesselRoles;
import io.committed.vessel.plugin.server.services.UserAccountDataRepository;
import io.committed.vessel.plugin.server.services.UserAccountService;


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

  @Bean
  @Profile("auth-mongo")
  public UserAccountDataRepository userAccountRepository(
      final ReactiveMongoRepositoryFactory repositoryFactory) {
    return repositoryFactory.getRepository(UserAccountDataRepository.class);
  }

  @Bean
  @Profile("auth-jpa")
  public UserAccountDataRepository userAccountRepository(
      final JpaRepositoryFactory repositoryFactory) {
    return repositoryFactory.getRepository(UserAccountDataRepository.class);
  }

  @Bean
  public UserAccountService userAccountService(final UserAccountDataRepository repository) {
    return new UserAccountService(repository);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
