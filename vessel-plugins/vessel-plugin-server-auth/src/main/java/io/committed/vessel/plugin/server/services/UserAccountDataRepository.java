package io.committed.vessel.plugin.server.services;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import io.committed.vessel.plugin.server.auth.dao.UserAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserAccountDataRepository extends ReactiveCrudRepository<UserAccount, String> {

  void deleteByUsername(String username);

  Flux<UserAccount> findByAuthorities(String authority);

  Mono<UserAccount> findByUsername(String username);

}
