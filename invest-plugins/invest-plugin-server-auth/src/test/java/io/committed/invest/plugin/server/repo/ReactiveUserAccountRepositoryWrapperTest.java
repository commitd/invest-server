package io.committed.invest.plugin.server.repo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

public class ReactiveUserAccountRepositoryWrapperTest {

  private UnreactiveUserAccountRepository repo;
  private ReactiveUserAccountRepositoryWrapper wrapper;

  @Before
  public void before() {
    repo = mock(UnreactiveUserAccountRepository.class);
    wrapper = new ReactiveUserAccountRepositoryWrapper(repo);
  }

  @Test
  public void testDeleteByUsername() {
    wrapper.deleteByUsername("username");
    verify(repo).deleteByUsername("username");
  }

  @Test
  public void testFindByAuthorities() {
    wrapper.findByAuthorities("a");
    verify(repo).findByAuthorities("a");
  }

  @Test
  public void testFindByUsername() {
    wrapper.findByUsername("username");
    verify(repo).findByUsername("username");
  }
}
