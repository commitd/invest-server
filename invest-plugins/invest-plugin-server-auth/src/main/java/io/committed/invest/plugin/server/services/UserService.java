package io.committed.invest.plugin.server.services;

import static java.util.stream.Collectors.toSet;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import io.committed.invest.core.auth.InvestRoles;
import io.committed.invest.plugin.server.auth.dao.UserAccount;
import io.committed.invest.plugin.server.repo.UserAccountRepository;

/** Service to manage specific business functions for user accounts. */
@Slf4j
public class UserService {

  private final UserAccountRepository userAccounts;
  private final PasswordEncoder passwordEncoder;

  public UserService(
      final UserAccountRepository userAccounts, final PasswordEncoder passwordEncoder) {
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
      mono.map(
              account -> {
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

  public Mono<UserAccount> findOrAddAccount(
      final String username,
      final String password,
      final String name,
      final String organisation,
      final Set<String> roles) {

    final Mono<UserAccount> mono = userAccounts.findByUsername(username);
    if (mono.hasElement().block()) {
      return mono;
    } else {
      final String encoded = encodePassword(password);
      Set<String> authorities =
          roles.stream().map(InvestRoles::fromRoleToAuthority).collect(toSet());
      final UserAccount account =
          new UserAccount(username, encoded, name, organisation, authorities);
      return userAccounts.save(account);
    }
  }

  public Flux<UserAccount> findAccounts() {
    return userAccounts.findAll();
  }

  public Mono<UserAccount> updateAccount(
      final String username,
      final String name,
      final String organisation,
      final Set<String> roles) {

    final UserAccount saved =
        userAccounts
            .findByUsername(username)
            .map(
                userAccount -> {
                  userAccount.setName(name);
                  userAccount.setOrganisation(organisation);
                  userAccount.setAuthorities(
                      roles.stream().map(InvestRoles::fromRoleToAuthority).collect(toSet()));
                  return userAccount;
                })
            .flatMap(userAccounts::save)
            .block();

    return Mono.justOrEmpty(saved);
  }

  public Mono<Void> deleteAccount(final String username) {
    Optional<UserAccount> account = userAccounts.findByUsername(username).blockOptional();
    return account.map(userAccounts::delete).orElse(Mono.empty());
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
}
