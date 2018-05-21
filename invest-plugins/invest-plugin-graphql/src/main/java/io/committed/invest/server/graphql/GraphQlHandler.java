package io.committed.invest.server.graphql;

import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import io.committed.invest.core.exceptions.InvestRuntimeException;
import io.committed.invest.core.graphql.InvestRootContext;
import io.committed.invest.server.graphql.data.GraphQlQuery;
import graphql.ExecutionInput;
import graphql.ExecutionInput.Builder;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.introspection.IntrospectionQuery;
import graphql.schema.GraphQLSchema;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Executes GraphQL queries and mutations.
 *
 * <p>See http://graphql.org/learn/serving-over-http/ for more details of conventions etc.
 */
@Component
@Slf4j
public class GraphQlHandler {

  private static final TypeReference<Map<String, Object>> mapStringObject =
      new TypeReference<Map<String, Object>>() {};

  private final GraphQL graphQL;
  private final ObjectMapper mapper;

  @Autowired
  public GraphQlHandler(final GraphQLSchema graphQLSchema, final ObjectMapper mapper) {
    this.mapper = mapper;
    graphQL = GraphQL.newGraphQL(graphQLSchema).build();
  }

  public Mono<ServerResponse> postQuery(final ServerRequest request) {
    
    Mono<ExecutionResult> result = request
    .bodyToMono(GraphQlQuery.class)
    .map(
        q ->
            ExecutionInput.newExecutionInput()
                .operationName(q.getOperationName())
                .query(q.getQuery())
                .variables(q.getVariables())
                .context(buildContext(request))
                .build())
    .flatMap(this::performQuery);
    
    return convertResultToServerResponse(result);
  }

  public Mono<ServerResponse> getQuery(final ServerRequest request) {

    final Optional<String> query = request.queryParam("query");
    final Optional<String> operationName = request.queryParam("operationName");
    final Optional<String> variablesString = request.queryParam("variables");

    Builder input = ExecutionInput.newExecutionInput();

    if (query.isPresent()) {
      input = input.query(query.get());
    }

    if (operationName.isPresent()) {
      input = input.operationName(operationName.get());
    }

    if (variablesString.isPresent()) {
      try {
        input = input.variables(mapper.readValue(variablesString.get(), mapStringObject));
      } catch (final Exception e) {
        log.warn("Dropping variables unable to deserialise", e);
      }
    }

    final InvestRootContext context = buildContext(request);
    input = input.context(context);

    final Mono<ExecutionResult> result = performQuery(input.build());
    
    return convertResultToServerResponse(result);
  }

  private InvestRootContext buildContext(final ServerRequest request) {
    return InvestRootContext.builder()
        // TODO: We should be able to use request.principal here, but it doesn't seem to have any
        // data in (which it did do in spring boot M5).
        // It seems like the WebSessionServerSecurityContextRepository.load function is never
        // called, but I can't see why.
        // Whilst this should work for our purpposes it means that prinicpal isn't available to
        // other users.
        .authentication(
            request
                .session()
                .flatMap(
                    s -> {
                      final SecurityContext sc = s.getAttribute("USER");
                      return sc != null && sc.getAuthentication() != null
                          ? Mono.just(sc.getAuthentication())
                          : Mono.empty();
                    }))
        .session(request.session())
        .build();
  }

  // Surppressed warning because this is used a a WebFlux hander which needs to have the functional
  // interface.
  @SuppressWarnings("squid:S1172")
  public Mono<ServerResponse> getSchema(final ServerRequest request) {
    final ExecutionInput input =
        ExecutionInput.newExecutionInput().query(IntrospectionQuery.INTROSPECTION_QUERY).build();
    final Mono<ExecutionResult> result = performQuery(input);
    return convertResultToServerResponse(result);
  }

  protected Mono<ExecutionResult> performQuery(final ExecutionInput input) {
    try {
      Mono<ExecutionResult> blockingWrapper = Mono.fromCallable(() -> { 
        return graphQL.execute(input);

    });
    return blockingWrapper = blockingWrapper.subscribeOn(Schedulers.elastic());
    } catch (final Exception e) {
      final Throwable rootCause = Throwables.getRootCause(e);
      log.debug("Exception was: ", e);
      throw new InvestRuntimeException(rootCause.getMessage());
    }
  }
  
  private Mono<ServerResponse> convertResultToServerResponse(final Mono<ExecutionResult> result) {
    return ServerResponse.ok().body(result.map(ExecutionResult::toSpecification), Map.class);
  }
}
