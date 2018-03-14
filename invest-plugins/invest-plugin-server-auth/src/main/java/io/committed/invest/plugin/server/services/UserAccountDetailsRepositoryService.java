package io.committed.invest.plugin.server.services;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import io.committed.invest.plugin.server.auth.dao.UserAccount;
import io.committed.invest.plugin.server.auth.utils.AuthUtils;
import io.committed.invest.plugin.server.repo.UserAccountRepository;
import reactor.core.publisher.Mono;

/**
 * Implementation of Springs's ReactiveUserDetailsService, backed by a UserAccountRepository.
 *
 */
public class UserAccountDetailsRepositoryService implements ReactiveUserDetailsService {

  private final UserAccountRepository repository;

  public UserAccountDetailsRepositoryService(final UserAccountRepository repository) {
    this.repository = repository;
  }

  private UserDetails toUser(final UserAccount account) {
    return new User(account.getUsername(), account.getPassword(), account.isEnabled(),
        !account.isExpired(), !account.isPasswordExpired(), !account.isLocked(),
        AuthUtils.toGrantAuthorities(account.getAuthorities()));
  }

  @Override
  public Mono<UserDetails> findByUsername(final String username) {

    // Javadoc says that we should do the password check in addititonalAuthenticationChecks, rather
    // than using here in the find by

    return repository.findByUsername(username).map(this::toUser);
  }
}
