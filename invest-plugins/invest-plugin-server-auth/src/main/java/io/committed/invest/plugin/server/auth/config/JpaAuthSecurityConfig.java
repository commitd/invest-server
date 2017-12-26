package io.committed.invest.plugin.server.auth.config;

import javax.persistence.EntityManager;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

import io.committed.invest.plugin.server.auth.dao.UserAccount;
import io.committed.invest.plugin.server.services.ReactiveUserAccountRepositoryWrapper;
import io.committed.invest.plugin.server.services.UnreactiveUserAccountRepository;
import io.committed.invest.plugin.server.services.UserAccountRepository;

@Configuration
@Profile("auth-jpa")
@EnableJpaRepositories
@EntityScan(basePackageClasses = UserAccount.class)
public class JpaAuthSecurityConfig extends AbstractWithAuthSecurityConfig {

  @Bean
  public UserAccountRepository userAccountRepository(final EntityManager entityManager) {
    final JpaRepositoryFactory repositoryFactory = new JpaRepositoryFactory(entityManager);
    final UnreactiveUserAccountRepository repo =
        repositoryFactory.getRepository(UnreactiveUserAccountRepository.class);
    return new ReactiveUserAccountRepositoryWrapper(repo);
  }

}
