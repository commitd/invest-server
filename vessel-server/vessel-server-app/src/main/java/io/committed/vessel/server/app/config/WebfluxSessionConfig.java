package io.committed.vessel.server.app.config;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.EnableSpringWebSession;
import org.springframework.session.MapReactorSessionRepository;

@Configuration
@EnableSpringWebSession
public class WebfluxSessionConfig {

  @Bean
  public MapReactorSessionRepository reactorSessionRepository() {
    return new MapReactorSessionRepository(new ConcurrentHashMap<>());
  }

  // Support Session in header not in cookie
  // @Bean
  // public HeaderWebSessionIdResolver webSessionIdResolver() {
  // return new HeaderWebSessionIdResolver();
  // }

  // Support Session in header not in cookie
  // @Bean
  // public CookieWebSessionIdResolver webSessionIdResolver() {
  // return new CookieWebSessionIdResolver();
  // }
}
