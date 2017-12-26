package io.committed.invest.server.core.config;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecureRandomConfig {

  @Bean
  @ConditionalOnMissingBean(SecureRandom.class)
  public SecureRandom secureRandom() {
    return new SecureRandom();
  }

  @Bean
  @ConditionalOnMissingBean(Random.class)
  public Random random() {
    return new Random();
  }
}
