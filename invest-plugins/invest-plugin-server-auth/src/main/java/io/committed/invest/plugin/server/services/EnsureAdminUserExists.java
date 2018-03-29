package io.committed.invest.plugin.server.services;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Mono;

import io.committed.invest.core.auth.InvestRoles;
import io.committed.invest.plugin.server.auth.dao.UserAccount;
import io.committed.invest.plugin.server.auth.utils.AuthUtils;
import io.committed.invest.plugin.server.repo.UserAccountRepository;

/**
 * Ensures that an admin user is available on the dataset.
 *
 * <p>This prevent users from add .
 *
 * <p>WARNING: Logs the password to the console on startup (when run).
 */
@Component
@Slf4j
public class EnsureAdminUserExists implements ApplicationListener<ContextRefreshedEvent> {

  private static final String DEFAULT_ADMIN_USERNAME = "admin";

  private final UserService securityService;
  private final UserAccountRepository userAccounts;

  @Autowired
  public EnsureAdminUserExists(
      final UserService securityService, final UserAccountRepository userAccounts) {
    this.securityService = securityService;
    this.userAccounts = userAccounts;
  }

  public void ensureUser() {
    userAccounts
        .findAll()
        .any(u -> u.hasAuthority(InvestRoles.ADMINISTRATOR_AUTHORITY))
        .flatMap(
            exists -> {
              if (exists) {
                log.info("Admin user exists in the database, not creating a default admin");
                return Mono.just(true);
              }

              final String password = securityService.generateRandomPassword();
              final Mono<UserAccount> account =
                  securityService.findOrAddAccount(
                      DEFAULT_ADMIN_USERNAME,
                      password,
                      "admin",
                      "",
                      AuthUtils.toSet(InvestRoles.ADMINISTRATOR));
              log.info("Creating user {} with password {}", DEFAULT_ADMIN_USERNAME, password);

              return account.hasElement();
            })
        .subscribe(
            exists -> {
              if (!exists) {
                log.error(
                    "Unable to create admin user {}, no admin users are present in the database",
                    DEFAULT_ADMIN_USERNAME);
              }
            });
  }

  @Override
  @Transactional
  public void onApplicationEvent(final ContextRefreshedEvent event) {
    ensureUser();
  }
}
