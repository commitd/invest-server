package io.committed.invest.plugin.server.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import io.committed.invest.plugin.server.auth.dao.UserAccount;

/** Spring Data repository for User accounts. */
public interface UserAccountRepository extends ReactiveCrudRepository<UserAccount, String> {

  void deleteByUsername(String username);

  Flux<UserAccount> findByAuthorities(String authority);

  Mono<UserAccount> findByUsername(String username);
}
