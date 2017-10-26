package io.committed.vessel.plugin.server.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.support.ReactiveMongoRepositoryFactory;

import io.committed.vessel.plugin.server.services.UserAccountRepository;

@Configuration
@Profile("auth-mongo")

public class MongoAuthSecurityConfig extends AbstractWithAuthSecurityConfig {

  @Bean
  public UserAccountRepository userAccountRepository(
      final ReactiveMongoRepositoryFactory repositoryFactory) {
    return repositoryFactory.getRepository(UserAccountRepository.class);
  }

}
