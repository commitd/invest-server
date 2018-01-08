package io.committed.invest.server.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import io.committed.invest.server.core.ServerCoreConfiguration;

@SpringBootApplication
@Import({ServerCoreConfiguration.class})
@EnableAsync
@EnableScheduling
// Disable webflux security until it's actually enabled by profile
@EnableAutoConfiguration(exclude = {ReactiveSecurityAutoConfiguration.class,})
@EnableConfigurationProperties
public class Invest {

  public static void main(final String[] args) {
    SpringApplication.run(Invest.class, args);
  }

}
