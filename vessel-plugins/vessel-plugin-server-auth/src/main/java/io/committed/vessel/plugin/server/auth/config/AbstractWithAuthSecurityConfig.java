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
import io.committed.vessel.plugin.server.services.ReactiveUserAccountRepositoryWrapper;
import io.committed.vessel.plugin.server.services.UnreactiveUserAccountRepository;
import io.committed.vessel.plugin.server.services.UserAccountDetailsRepositoryService;
import io.committed.vessel.plugin.server.services.UserAccountRepository;


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
  public UserAccountRepository userAccountRepository(
      final ReactiveMongoRepositoryFactory repositoryFactory) {
    return repositoryFactory.getRepository(UserAccountRepository.class);
  }

  @Bean
  @Profile("auth-jpa")
  public UserAccountRepository userAccountRepository(
      final JpaRepositoryFactory repositoryFactory) {
    final UnreactiveUserAccountRepository repo =
        repositoryFactory.getRepository(UnreactiveUserAccountRepository.class);
    return new ReactiveUserAccountRepositoryWrapper(repo);
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
