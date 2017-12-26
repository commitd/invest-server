package io.committed.invest.plugin.server.services;

import io.committed.invest.plugin.server.auth.dao.UserAccount;
import io.committed.spring.reactive.repositories.ReactiveRepositoryWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactiveUserAccountRepositoryWrapper
    extends ReactiveRepositoryWrapper<UserAccount, String, UnreactiveUserAccountRepository>
    implements UserAccountRepository {

  public ReactiveUserAccountRepositoryWrapper(final UnreactiveUserAccountRepository repo) {
    super(repo);
  }

  @Override
  public void deleteByUsername(final String username) {
    repo.deleteByUsername(username);

  }

  @Override
  public Flux<UserAccount> findByAuthorities(final String authority) {
    return Flux.fromStream(repo.findByAuthorities(authority));
  }

  @Override
  public Mono<UserAccount> findByUsername(final String username) {
    return Mono.justOrEmpty(repo.findByUsername(username));
  }



}
