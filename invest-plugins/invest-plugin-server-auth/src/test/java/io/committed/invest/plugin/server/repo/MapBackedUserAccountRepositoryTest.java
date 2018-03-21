package io.committed.invest.plugin.server.repo;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import io.committed.invest.plugin.server.auth.dao.UserAccount;
import io.committed.invest.plugin.server.auth.utils.AuthUtils;

public class MapBackedUserAccountRepositoryTest {

  private MapBackedUserAccountRepository repository;
  private UserAccount newUser;
  private UserAccount existingUser;

  @Before
  public void before() {
    repository = new MapBackedUserAccountRepository();

    newUser = UserAccount.builder().username("username").authorities(AuthUtils.toSet("a1")).build();

    existingUser =
        repository.save(UserAccount.builder().username("oldUser").authorities(AuthUtils.toSet("a1", "a2")).build());
  }

  @Test
  public void testSave() {
    assertThat(repository.count()).isEqualTo(1);

    repository.save(newUser);

    assertThat(repository.count()).isEqualTo(2);

  }

  @Test
  public void testSaveAll() {
    repository.saveAll(Arrays.asList(newUser, existingUser));

    assertThat(repository.count()).isEqualTo(2);
  }

  @Test
  public void testFindById() {
    assertThat(repository.findById(existingUser.getId())).isPresent();

    assertThat(repository.findById("missing")).isEmpty();

  }

  @Test
  public void testExistsById() {
    assertThat(repository.existsById(existingUser.getId())).isTrue();

    assertThat(repository.existsById("missing")).isFalse();

  }

  @Test
  public void testFindAll() {
    assertThat(repository.findAll()).containsExactly(existingUser);

  }

  @Test
  public void testFindAllById() {
    assertThat(repository.findAllById(Arrays.asList(existingUser.getId()))).containsExactly(existingUser);
  }

  @Test
  public void testCount() {
    assertThat(repository.count()).isEqualTo(1);
  }

  @Test
  public void testDeleteById() {
    repository.deleteById(existingUser.getId());
    assertThat(repository.count()).isEqualTo(0);
  }

  @Test
  public void testDelete() {
    repository.delete(existingUser);
    assertThat(repository.count()).isEqualTo(0);
  }

  @Test
  public void testDeleteAllIterableOfQextendsUserAccount() {
    repository.deleteAll(Arrays.asList(existingUser));
    assertThat(repository.count()).isEqualTo(0);
  }

  @Test
  public void testDeleteAll() {
    repository.deleteAll();
    assertThat(repository.count()).isEqualTo(0);
  }

  @Test
  public void testDeleteByUsername() {
    repository.deleteByUsername(existingUser.getUsername());
    assertThat(repository.count()).isEqualTo(0);

  }

  @Test
  public void testFindByAuthorities() {
    assertThat(repository.findByAuthorities("a1")).contains(existingUser);
  }

  @Test
  public void testFindByUsername() {
    assertThat(repository.findByUsername(existingUser.getUsername())).contains(existingUser);
  }

}
