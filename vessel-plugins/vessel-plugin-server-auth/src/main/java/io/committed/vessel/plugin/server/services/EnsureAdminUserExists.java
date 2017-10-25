package io.committed.vessel.plugin.server.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.committed.vessel.plugin.server.auth.constants.VesselRoles;
import io.committed.vessel.plugin.server.auth.dao.UserAccount;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class EnsureAdminUserExists {

  private final SecurityService securityService;
  private final UserAccountDataRepository userAccounts;

  @Autowired
  public EnsureAdminUserExists(final SecurityService securityService,
      final UserAccountDataRepository userAccounts) {
    this.securityService = securityService;
    this.userAccounts = userAccounts;
  }

  @PostConstruct
  @Transactional
  public void ensureUser() throws Exception {
    final Mono<Boolean> adminUser =
        userAccounts.findAll()
            .any(u -> u.hasAuthority(VesselRoles.ADMINISTRATOR_AUTHORITY));

    if (adminUser.block()) {
      log.info("Admin user exists in the database, not creating a default admin");
      return;
    }

    final String password = securityService.generateRandomPassword();
    final String username = "admin";
    final Mono<UserAccount> account =
        securityService.findOrAddAccount(username, password, "admin", "",
            securityService.toSet(VesselRoles.ADMINISTRATOR_AUTHORITY));

    if (account.hasElement().block()) {
      log.info("Created user {} with password {}", username, password);

    } else {
      log.error("Unable to create admin user {}, no admin users are present in the database",
          username);

    }


  }
}
