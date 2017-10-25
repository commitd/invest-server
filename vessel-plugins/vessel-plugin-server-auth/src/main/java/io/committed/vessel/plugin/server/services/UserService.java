package io.committed.vessel.plugin.server.services;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import io.committed.vessel.plugin.server.auth.dao.UserAccount;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


@Slf4j
public class UserService {
  private final UserAccountRepository userAccounts;
  private final PasswordEncoder passwordEncoder;

  public UserService(final UserAccountRepository userAccounts,
      final PasswordEncoder passwordEncoder) {
    this.userAccounts = userAccounts;
    this.passwordEncoder = passwordEncoder;
  }

  public void changePassword(final String username, final String password) {
    if (StringUtils.isEmpty(password)) {
      log.warn("Attempt to set an empty password for {}", username);
      return;
    }

    final Mono<UserAccount> mono = userAccounts.findByUsername(username);
    if (mono.hasElement().block()) {
      mono
          .map(account -> {
            final String encoded = passwordEncoder.encode(password);
            account.setPassword(encoded);
            return account;
          })
          .flatMap(userAccounts::save)
          .block();
      log.info("Password change for {}", username);
    } else {
      log.warn("Attempt to set an password for non-existant {}", username);
    }
  }

  public String encodePassword(final String password) {
    return passwordEncoder.encode(password);
  }

  public Mono<UserAccount> findOrAddAccount(final String username, final String password,
      final String name, final String organisation, final Set<String> roles) {

    final Mono<UserAccount> mono = userAccounts.findByUsername(username);
    if (mono.hasElement().block()) {
      return mono;
    } else {
      final String encoded = encodePassword(password);
      final UserAccount account = new UserAccount(username, encoded, name, organisation, roles);
      return userAccounts.save(account);
    }
  }

  public Mono<UserAccount> updateAccount(final String username, final String name,
      final String organisation,
      final Set<String> roles) {

    final UserAccount saved = userAccounts.findByUsername(username)
        .map(userAccount -> {
          userAccount.setName(name);
          userAccount.setOrganisation(organisation);
          userAccount.setAuthorities(roles);
          return userAccount;
        })
        .flatMap(userAccounts::save)
        .block();

    return Mono.justOrEmpty(saved);
  }

  public Mono<UserAccount> getAccount(final Principal principal) {
    return getAccount(principal.getName());
  }

  public Mono<UserAccount> getAccount(final String username) {
    return userAccounts.findByUsername(username);
  }

  public String generateRandomPassword() {
    return KeyGenerators.string().generateKey();
  }

  public boolean hasAuthoritory(final Authentication authentication,
      final String... authority) {
    final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    if (authorities == null || authorities.isEmpty()) {
      return false;
    }

    return Arrays.stream(authority).anyMatch(
        auth -> authorities.stream().anyMatch(a -> a.getAuthority().equalsIgnoreCase(auth)));
  }

  public Set<String> toSet(final String... roles) {
    return new HashSet<>(Arrays.asList(roles));
  }
}
