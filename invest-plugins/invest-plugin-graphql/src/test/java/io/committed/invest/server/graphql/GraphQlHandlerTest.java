package io.committed.invest.server.graphql;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.committed.invest.server.graphql.mappers.FluxToCollectionTypeAdapter;
import io.committed.invest.server.graphql.mappers.MonoAdapter;
import io.committed.invest.server.graphql.mappers.PropertiesListScalarAdapter;
import io.committed.invest.server.graphql.mappers.PropertiesMapScalarAdapter;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import reactor.core.publisher.Mono;

public class GraphQlHandlerTest {

  private static final String TESTING_WITH_RTE = "Testing with RTE";
  private static final String TESTING_WITH_FLUX = "Testing with Flux";


  private GraphQlHandler graphQlHandler;


  @Before
  public void before() {
    final ObjectMapper mapper = new ObjectMapper();
    final GraphQLSchema graphQLSchema = new GraphQLSchemaGenerator()
        .withValueMapperFactory(new JacksonValueMapperFactory())
        // Deal with reactive types
        .withTypeMappers(new PropertiesListScalarAdapter(), new PropertiesMapScalarAdapter())
        .withTypeAdapters(new MonoAdapter(), new FluxToCollectionTypeAdapter())
        .withOutputConverters(
            new MonoAdapter(),
            new FluxToCollectionTypeAdapter(),
            new PropertiesListScalarAdapter(),
            new PropertiesMapScalarAdapter())
        .withInputConverters(new MonoAdapter(), new FluxToCollectionTypeAdapter())
        .withDefaults()
        .withOperationsFromSingleton(new RuntimeExceptionResolver())
        .withOperationsFromSingleton(new ExceptionInFluxResolver())
        .generate();
    graphQlHandler = new GraphQlHandler(graphQLSchema, mapper);


  }

  @Test
  public void testPerformQueryRuntimeExceptionInResolver() {
    callForError("runtime", TESTING_WITH_RTE);
  }


  @Test
  public void testPerformQueryRuntimeExceptionInResolverInFlux() {
    callForError("runtime", TESTING_WITH_FLUX);

  }

  private void callForError(final String method, final String message) {

    final ExecutionInput input = ExecutionInput.newExecutionInput()
        .query(String.format("query { %s }", method))
        .build();
    final Mono<ExecutionResult> result = graphQlHandler.performQuery(input);


    assertThat(result.block().getErrors()).isNotEmpty();

  }

  public static class RuntimeExceptionResolver {


    @GraphQLQuery(name = "runtime")
    public String runtime() {
      throw new RuntimeException(TESTING_WITH_RTE);
    }
  }

  public static class ExceptionInFluxResolver {


    @GraphQLQuery(name = "influx")
    public Mono<String> runtime() {
      return Mono.just("testing").map(s -> {
        throw new RuntimeException(TESTING_WITH_FLUX);
      });
    }
  }
}
