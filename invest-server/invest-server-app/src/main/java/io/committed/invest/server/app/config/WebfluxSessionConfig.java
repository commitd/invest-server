package io.committed.invest.server.app.config;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.ReactiveMapSessionRepository;
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession;
import org.springframework.web.server.session.HeaderWebSessionIdResolver;

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

  // Support Session in header in cookie
  // @Bean
  // public CookieWebSessionIdResolver webSessionIdResolver() {
  // return new CookieWebSessionIdResolver();
  // }
}
