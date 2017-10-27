package io.committed.vessel.core.graphql;

import java.security.Principal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import reactor.core.publisher.Mono;

@Value
@Builder
@AllArgsConstructor
public class Context {

  // TODO: This is a Websession!
  private Mono<? extends Object> session;

  private Mono<? extends Principal> authentication;

}
