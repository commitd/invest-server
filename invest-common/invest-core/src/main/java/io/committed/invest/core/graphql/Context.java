package io.committed.invest.core.graphql;

import org.springframework.security.core.Authentication;
import org.springframework.web.server.WebSession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import reactor.core.publisher.Mono;

@Value
@Builder
@AllArgsConstructor
public class Context {

  private Mono<WebSession> session;

  private Mono<Authentication> authentication;

}
