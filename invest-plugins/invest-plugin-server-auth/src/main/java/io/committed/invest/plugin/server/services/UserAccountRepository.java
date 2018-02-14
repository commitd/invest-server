package io.committed.invest.plugin.server.services;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import io.committed.invest.plugin.server.auth.dao.UserAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserAccountRepository extends ReactiveCrudRepository<UserAccount, String> {

  void deleteByUsername(String username);

  Flux<UserAccount> findByAuthorities(String authority);

  Mono<UserAccount> findByUsername(String username);

}
