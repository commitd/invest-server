package io.committed.invest.plugin.server.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.repository.support.ReactiveMongoRepositoryFactory;
import io.committed.invest.plugin.server.repo.UserAccountRepository;

/**
 * Mongo backed user database.
 *
 */
@Configuration
@Profile("auth-mongo")
public class MongoAuthSecurityConfig extends AbstractWithAuthSecurityConfig {

  @Bean
  public UserAccountRepository userAccountRepository(final ReactiveMongoOperations operations) {
    final ReactiveMongoRepositoryFactory repositoryFactory =
        new ReactiveMongoRepositoryFactory(operations);
    return repositoryFactory.getRepository(UserAccountRepository.class);
  }

}
