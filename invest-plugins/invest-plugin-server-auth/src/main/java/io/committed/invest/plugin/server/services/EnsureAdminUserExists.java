package io.committed.invest.plugin.server.services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import io.committed.invest.plugin.server.auth.constants.InvestRoles;
import io.committed.invest.plugin.server.auth.dao.UserAccount;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class EnsureAdminUserExists implements ApplicationListener<ContextRefreshedEvent> {

  private final UserService securityService;
  private final UserAccountRepository userAccounts;

  @Autowired
  public EnsureAdminUserExists(final UserService securityService,
      final UserAccountRepository userAccounts) {
    this.securityService = securityService;
    this.userAccounts = userAccounts;
  }


  public void ensureUser() {
    final Mono<Boolean> adminUser =
        userAccounts.findAll().any(u -> u.hasAuthority(InvestRoles.ROLE_ADMINISTRATOR));

    if (adminUser.block()) {
      log.info("Admin user exists in the database, not creating a default admin");
      return;
    }

    final String password = securityService.generateRandomPassword();
    final String username = "admin";
    final Mono<UserAccount> account = securityService.findOrAddAccount(username, password, "admin",
        "", securityService.toSet(InvestRoles.ROLE_ADMINISTRATOR));

    if (account.hasElement().block()) {
      log.info("Created user {} with password {}", username, password);

    } else {
      log.error("Unable to create admin user {}, no admin users are present in the database",
          username);

    }
  }

  @Override
  @Transactional
  public void onApplicationEvent(final ContextRefreshedEvent event) {
    ensureUser();
  }
}
