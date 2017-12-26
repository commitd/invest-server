package io.committed.invest.plugin.server.services;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;

import io.committed.invest.plugin.server.auth.dao.UserAccount;

public interface UnreactiveUserAccountRepository extends CrudRepository<UserAccount, String> {

  void deleteByUsername(String username);

  Stream<UserAccount> findByAuthorities(String authority);

  Optional<UserAccount> findByUsername(String username);

}
