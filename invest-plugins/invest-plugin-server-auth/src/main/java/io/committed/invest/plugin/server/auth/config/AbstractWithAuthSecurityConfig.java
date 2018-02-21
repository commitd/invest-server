package io.committed.invest.plugin.server.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import io.committed.invest.core.auth.InvestRoles;
import io.committed.invest.core.services.UiUrlService;
import io.committed.invest.plugin.server.auth.graphql.AuthGraphQlResolver;
import io.committed.invest.plugin.server.services.EnsureAdminUserExists;
import io.committed.invest.plugin.server.services.UserAccountDetailsRepositoryService;
import io.committed.invest.plugin.server.services.UserAccountRepository;
import io.committed.invest.plugin.server.services.UserService;


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public abstract class AbstractWithAuthSecurityConfig {

  @Autowired
  UiUrlService urlService;

  @Bean
  public SecurityWebFilterChain springWebFilterChain(final ServerHttpSecurity http,
      final ServerSecurityContextRepository securityContextRepository,
      final ReactiveAuthenticationManager authenticationManager) {

    // For the /view we want to allow iframe acccess
    // TODO: Can we limit this just to /ui?
    http.headers().frameOptions().disable();

    http.csrf().disable();

    http.authenticationManager(authenticationManager);
    http.securityContextRepository(securityContextRepository);

    http.authorizeExchange()
        // Allow access to static files inside the UI
        .pathMatchers(urlService.getContextPath() + "/**").permitAll().pathMatchers("/actuator/**")
        .hasRole(InvestRoles.ROLE_ADMINISTRATOR).anyExchange().permitAll();


    return http.build();
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

  @Bean
  public AuthGraphQlResolver authController(final UserService userService,
      final ReactiveAuthenticationManager authenticationManager) {
    return new AuthGraphQlResolver(authenticationManager, userService);
  }

  // Store our security into in the websession
  @Bean
  public ServerSecurityContextRepository securityContextRepository() {
    return new WebSessionServerSecurityContextRepository();
  }


  @Bean
  public UserDetailsRepositoryReactiveAuthenticationManager authenticationManager(
      final ReactiveUserDetailsService reactiveUserDetailsService,
      final PasswordEncoder passwordEncoder) {
    final UserDetailsRepositoryReactiveAuthenticationManager manager =
        new UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService);
    manager.setPasswordEncoder(passwordEncoder);
    return manager;
  }


}
