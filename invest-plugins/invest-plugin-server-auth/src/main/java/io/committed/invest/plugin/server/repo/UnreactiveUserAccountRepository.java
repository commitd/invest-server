package io.committed.invest.plugin.server.repo;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;

import io.committed.invest.plugin.server.auth.dao.UserAccount;

/** Non-reactive Spring data repository for user accounts. */
public interface UnreactiveUserAccountRepository extends CrudRepository<UserAccount, String> {

  void deleteByUsername(String username);

  Stream<UserAccount> findByAuthorities(String authority);

  Optional<UserAccount> findByUsername(String username);
}
