package io.committed.invest.plugin.server.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import java.security.Principal;
import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.committed.invest.plugin.server.auth.dao.UserAccount;
import io.committed.invest.plugin.server.auth.utils.AuthUtils;
import io.committed.invest.plugin.server.repo.MapBackedUserAccountRepository;
import io.committed.invest.plugin.server.repo.ReactiveUserAccountRepositoryWrapper;
import io.committed.invest.plugin.server.repo.UserAccountRepository;

public class UserServiceTest {

  private UserAccountRepository userAccounts;
  private PasswordEncoder passwordEncoder;
  private UserService userService;
  private MapBackedUserAccountRepository repo;

  @Before
  public void before() {
    passwordEncoder = new PasswordEncoder() {

      @Override
      public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
      }

      @Override
      public String encode(final CharSequence rawPassword) {
        // Just change the password so they arne't the same!
        return "!" + rawPassword;
      }
    };
    repo = new MapBackedUserAccountRepository();
    userAccounts = new ReactiveUserAccountRepositoryWrapper(repo);
    userService = new UserService(userAccounts, passwordEncoder);
  }

  @Test
  public void testChangePassword() {
    repo.save(UserAccount.builder().username("username").password("different").build());
    userService.changePassword("username", "password");
    assertThat(repo.findByUsername("username").get().getPassword()).isEqualTo(passwordEncoder.encode("password"));

  }

  @Test
  public void testEncodePassword() {
    assertThat(userService.encodePassword("password")).isEqualTo(passwordEncoder.encode("password"));
  }

  @Test
  public void testFindExistingAccount() {
    repo.save(UserAccount.builder().username("username").password("different").build());

    userService.findOrAddAccount("username", "password", "name", "organisation", new HashSet<>());

    assertThat(repo.findByUsername("username")).isPresent();
    assertThat(repo.findByUsername("username").get().getPassword()).isEqualTo("different");

  }

  @Test
  public void testAddNewAccount() {
    assertThat(repo.findByUsername("username")).isNotPresent();

    userService.findOrAddAccount("username", "password", "name", "organisation", new HashSet<>());

    assertThat(repo.findByUsername("username")).isPresent();
  }

  @Test
  public void testGeneratorRandomPassword() {
    assertThat(userService.generateRandomPassword()).isNotBlank();
    assertThat(userService.generateRandomPassword().length()).isGreaterThan(8);

  }

  @Test
  public void testGetAccountFromPrincipal() {
    final Principal p = mock(Principal.class);
    doReturn("username").when(p).getName();

    assertThat(userService.getAccount(p).blockOptional()).isNotPresent();
  }

  @Test
  public void testGetAccountFromUsername() {
    repo.save(UserAccount.builder().username("username").password("different").build());
    assertThat(userService.getAccount("username").blockOptional()).isPresent();
  }

  @Test
  public void testUpdateAccount() {
    repo.save(UserAccount.builder().username("username").password("different").build());

    userService.updateAccount("username", "name1", "organisation1", AuthUtils.toSet("r1", "r2"));

    final UserAccount ua = repo.findByUsername("username").get();
    assertThat(ua.getAuthorities()).contains("r1", "r2");
    assertThat(ua.getOrganisation()).isEqualTo("organisation1");
    assertThat(ua.getName()).isEqualTo("name1");
    assertThat(ua.getPassword()).isEqualTo("different");


  }

}
