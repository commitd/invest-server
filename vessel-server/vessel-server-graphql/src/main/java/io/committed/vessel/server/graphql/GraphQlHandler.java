package io.committed.vessel.server.graphql;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.ExecutionInput;
import graphql.ExecutionInput.Builder;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.introspection.IntrospectionQuery;
import graphql.schema.GraphQLSchema;
import io.committed.vessel.server.graphql.data.GraphQlQuery;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


// See http://graphql.org/learn/serving-over-http/
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
    return ServerResponse.ok().body(
        request.bodyToMono(GraphQlQuery.class)
            .map(q -> ExecutionInput.newExecutionInput()
                .operationName(q.getOperationName())
                .query(q.getQuery())
                .variables(q.getVariables())
                .build())
            .map(this::performQuery)
            .map(ExecutionResult::toSpecification),
        Map.class);
  }

  public Mono<ServerResponse> getQuery(final ServerRequest request) {
    final Optional<Object> query = request.attribute("query");
    final Optional<Object> operationName = request.attribute("operationName");
    final Optional<Object> variablesString = request.attribute("variables");

    Builder input = ExecutionInput.newExecutionInput();

    if (query.isPresent()) {
      input = input.query(query.get().toString());
    }

    if (operationName.isPresent()) {
      input = input.operationName(operationName.get().toString());
    }

    if (variablesString.isPresent()) {
      try {
        input.variables(mapper.readValue(variablesString.get().toString(), mapStringObject));
      } catch (final Exception e) {
        log.warn("Dropping variables unable to deserialise", e);
      }
    }

    final ExecutionResult result = performQuery(input.build());
    return ServerResponse.ok().syncBody(result.toSpecification());

  }

  public Mono<ServerResponse> getSchema(final ServerRequest request) {
    final ExecutionInput input = ExecutionInput.newExecutionInput()
        .query(IntrospectionQuery.INTROSPECTION_QUERY)
        .build();
    final ExecutionResult result = performQuery(input);
    return ServerResponse.ok().syncBody(result.toSpecification());
  }

  private ExecutionResult performQuery(final ExecutionInput input) {
    return graphQL.execute(input);
  }
}
