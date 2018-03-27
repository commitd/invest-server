package io.committed.invest.plugin.server.services;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import io.committed.invest.core.auth.InvestRoles;
import io.committed.invest.plugin.server.auth.dao.UserAccount;
import io.committed.invest.plugin.server.auth.utils.AuthUtils;
import io.committed.invest.plugin.server.repo.MapBackedUserAccountRepository;
import io.committed.invest.plugin.server.repo.ReactiveUserAccountRepositoryWrapper;
import io.committed.invest.plugin.server.repo.UserAccountRepository;

public class EnsureAdminUserExistsTest {

  private MapBackedUserAccountRepository repository;
  private EnsureAdminUserExists ensureAdminUserExists;

  @Before
  public void before() {
    repository = new MapBackedUserAccountRepository();
    final UserAccountRepository userAccounts = new ReactiveUserAccountRepositoryWrapper(repository);
    ensureAdminUserExists =
        new EnsureAdminUserExists(new UserService(userAccounts, NoOpPasswordEncoder.getInstance()), userAccounts);
  }

  @Test
  public void testEnsureUserWhenNone() {
    assertThat(repository.findByUsername("admin")).isEmpty();
    assertThat(repository.findByAuthorities(InvestRoles.ADMINISTRATOR_AUTHORITY)).hasSize(0);

    ensureAdminUserExists.ensureUser();

    assertThat(repository.findByUsername("admin")).isPresent();
    assertThat(repository.findByAuthorities(InvestRoles.ADMINISTRATOR_AUTHORITY)).hasSize(1);

  }

  @Test
  public void testEnsureUserWhenExists() {
    repository.save(
        UserAccount.builder()
            .username("bob")
            .authorities(AuthUtils.toSet(InvestRoles.ADMINISTRATOR_AUTHORITY))
            .build());

    ensureAdminUserExists.ensureUser();

    assertThat(repository.findByUsername("admin")).isEmpty();
    assertThat(repository.findByUsername("bob")).isPresent();
    assertThat(repository.findByAuthorities(InvestRoles.ADMINISTRATOR_AUTHORITY)).hasSize(1);


  }

}
