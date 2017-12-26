package io.committed.invest.plugin.server.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

// Based on UserDetailsRepositoryAuthenticationManager, but adds the user information as details
public class PopulatingUserDetailsRepositoryAuthenticationManager
    implements ReactiveAuthenticationManager {
  private final ReactiveUserDetailsService repository;

  private PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();

  public PopulatingUserDetailsRepositoryAuthenticationManager(
      final ReactiveUserDetailsService userDetailsRepository) {
    Assert.notNull(userDetailsRepository, "userDetailsRepository cannot be null");
    this.repository = userDetailsRepository;
  }

  @Override
  public Mono<Authentication> authenticate(final Authentication authentication) {
    final String username = authentication.getName();
    return this.repository.findByUsername(username)
        .publishOn(Schedulers.parallel())
        .filter(u -> this.passwordEncoder.matches((String) authentication.getCredentials(),
            u.getPassword()))
        .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid Credentials")))
        .map(u -> {
          final UsernamePasswordAuthenticationToken upt =
              new UsernamePasswordAuthenticationToken(u, u.getPassword(), u.getAuthorities());
          upt.setDetails(u);
          return upt;
        });
  }

  public void setPasswordEncoder(final PasswordEncoder passwordEncoder) {
    Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
    this.passwordEncoder = passwordEncoder;
  }
}
