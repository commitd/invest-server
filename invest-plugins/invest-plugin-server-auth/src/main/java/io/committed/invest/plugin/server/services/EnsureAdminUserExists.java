package io.committed.invest.plugin.server.services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import io.committed.invest.core.auth.InvestRoles;
import io.committed.invest.plugin.server.auth.dao.UserAccount;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class EnsureAdminUserExists implements ApplicationListener<ContextRefreshedEvent> {

  private final UserService securityService;
  private final UserAccountRepository userAccounts;

  private final static String DEFAULT_ADMIN_USERNAME = "admin";


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
    final Mono<UserAccount> account = securityService.findOrAddAccount(DEFAULT_ADMIN_USERNAME, password, "admin",
        "", securityService.toSet(InvestRoles.ROLE_ADMINISTRATOR));

    if (account.hasElement().block()) {
      log.info("Created user {} with password {}", DEFAULT_ADMIN_USERNAME, password);

    } else {
      log.error("Unable to create admin user {}, no admin users are present in the database",
          DEFAULT_ADMIN_USERNAME);

    }
  }

  @Override
  @Transactional
  public void onApplicationEvent(final ContextRefreshedEvent event) {
    ensureUser();
  }
}
