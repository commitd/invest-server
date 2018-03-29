package io.committed.invest.plugin.server.auth.config;

import javax.persistence.EntityManager;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

import io.committed.invest.plugin.server.auth.dao.UserAccount;
import io.committed.invest.plugin.server.repo.ReactiveUserAccountRepositoryWrapper;
import io.committed.invest.plugin.server.repo.UnreactiveUserAccountRepository;
import io.committed.invest.plugin.server.repo.UserAccountRepository;

/**
 * JPA based user database.
 *
 * <p>This will use the Spring JPA Data configuration, see Spring Boot documentation for more
 * information for details.
 */
@Configuration
@Profile("auth-jpa")
@EnableJpaRepositories
@Import(DataSourceAutoConfiguration.class)
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
