package io.committed.vessel.plugin.server.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

import io.committed.vessel.plugin.server.services.ReactiveUserAccountRepositoryWrapper;
import io.committed.vessel.plugin.server.services.UnreactiveUserAccountRepository;
import io.committed.vessel.plugin.server.services.UserAccountRepository;

@Configuration
@Profile("auth-jpa")
public class JpaAuthSecurityConfig extends AbstractWithAuthSecurityConfig {



  @Bean
  public UserAccountRepository userAccountRepository(
      final JpaRepositoryFactory repositoryFactory) {
    final UnreactiveUserAccountRepository repo =
        repositoryFactory.getRepository(UnreactiveUserAccountRepository.class);
    return new ReactiveUserAccountRepositoryWrapper(repo);
  }

}
