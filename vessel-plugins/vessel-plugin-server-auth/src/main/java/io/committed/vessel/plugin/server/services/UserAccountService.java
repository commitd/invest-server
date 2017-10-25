package io.committed.vessel.plugin.server.services;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsRepository;

import io.committed.vessel.plugin.server.auth.dao.UserAccount;
import reactor.core.publisher.Mono;

public class UserAccountService implements UserDetailsRepository {

  private final UserAccountDataRepository repository;

  public UserAccountService(final UserAccountDataRepository repository) {
    this.repository = repository;
  }

  private UserDetails toUser(final UserAccount account) {
    return new User(account.getUsername(), account.getPassword(), account.isEnabled(),
        !account.isExpired(), !account.isPasswordExpired(), !account.isLocked(),
        toGrantAuthorities(account.getAuthorities()));
  }

  private Collection<? extends GrantedAuthority> toGrantAuthorities(final Set<String> authorities) {
    return authorities.stream().map(a -> new SimpleGrantedAuthority(a))
        .collect(Collectors.toList());
  }

  @Override
  public Mono<UserDetails> findByUsername(final String username) {

    // Javadoc says that we should do the password check in addititonalAuthenticationChecks, rather
    // than using here in the find by

    return repository.findByUsername(username)
        .map(this::toUser);
  }
}
