package io.committed.invest.core.graphql;

import org.springframework.security.core.Authentication;
import org.springframework.web.server.WebSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import reactor.core.publisher.Mono;

/**
 * The GraphQL root context setup by Invest.
 *
 * You may access this through SPQR using an argument annotationed with GraphQLRootContext.
 *
 * We found this approach necessary because of the disjoint between Spring Mono/FLux reouter
 * functions and the execution environment of GraphQL (which is threaded even though we often return
 * a flux or mono).
 *
 * As such the Spring session and authentication information is not available as method annotations
 * within GraphQL functions. Thus the most pragmatic approach is to use the root context to hold
 * this information. It is setup at the start of the GraphQL execution.
 *
 */
@Value
@Builder
@AllArgsConstructor
public class InvestRootContext {

  private Mono<WebSession> session;

  private Mono<Authentication> authentication;

}
