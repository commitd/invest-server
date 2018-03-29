package io.committed.invest.server.core.config;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.ReactiveMapSessionRepository;
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession;
import org.springframework.web.server.session.HeaderWebSessionIdResolver;

/**
 * Configuration for Web Flux sessions.
 *
 * <p>This will use inmemory session management (see {@link ReactiveMapSessionRepository}) and
 * session in HTTP header {@link HeaderWebSessionIdResolver}.
 */
@Configuration
@EnableSpringWebSession
public class WebfluxSessionConfig {

  @Bean
  public ReactiveMapSessionRepository reactorSessionRepository() {
    return new ReactiveMapSessionRepository(new ConcurrentHashMap<>());
  }

  // Support Session in header not in cookie
  @Bean
  public HeaderWebSessionIdResolver webSessionIdResolver() {
    return new HeaderWebSessionIdResolver();
  }
}
