package io.committed.invest.server.graphql;

import java.util.Collections;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.ClassUtils;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.schema.GraphQLSchema;
import graphql.schema.idl.SchemaPrinter;

import io.committed.invest.extensions.annotations.GraphQLService;
import io.committed.invest.server.graphql.data.GraphQlServices;
import io.committed.invest.server.graphql.mappers.FluxToCollectionTypeAdapter;
import io.committed.invest.server.graphql.mappers.MonoAdapter;
import io.committed.invest.server.graphql.mappers.PropertiesListScalarAdapter;
import io.committed.invest.server.graphql.mappers.PropertiesMapScalarAdapter;

import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;

/**
 * Spring Configuration for GraphQL.
 *
 * <p>This acts to:
 *
 * <ul>
 *   <li>Find all beans which have {@link GraphQLService} annotations and expose a {@link
 *       GraphQlServices} bean with them
 *   <li>Based on the GraphQlServices beans create a schema
 *   <li>Expose a graphQLHandler for the schema which can be used to execute graphQLqueries
 *   <li>Export a RouterFunction for /graphql which passes GraphQL to the handler
 * </ul>
 */
@Configuration
@Slf4j
public class GraphQlConfig {

  @Autowired private ApplicationContext context;

  @Bean
  public RouterFunction<ServerResponse> graphQlRouterFunction(final GraphQlHandler handler) {
    return RouterFunctions.route(
            RequestPredicates.POST("/graphql")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
            handler::postQuery)
        .andRoute(RequestPredicates.GET("/graphql"), handler::getQuery)
        .andRoute(RequestPredicates.GET("/schema.json"), handler::getSchema);
  }

  @Bean
  public GraphQlHandler graphQlHandler(final GraphQLSchema schema, final ObjectMapper mapper) {
    return new GraphQlHandler(schema, mapper);
  }

  @Bean
  public GraphQlServices graphQlServices() {
    final Map<String, Object> beansWithAnnotation =
        context.getBeansWithAnnotation(GraphQLService.class);

    return new GraphQlServices(Collections.unmodifiableCollection(beansWithAnnotation.values()));
  }

  @Bean
  public GraphQLSchema schema(final GraphQlServices services) {

    GraphQLSchemaGenerator factory =
        new GraphQLSchemaGenerator()
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
            // Default MUST be after the Mono/Flux adapters so the they take priority over the
            // PublisherMapper
            .withDefaults();
    // At least one service must be registered otherwise GraphQL will thrown an exception
    if (services.getServices().isEmpty()) {
      factory = factory.withOperationsFromSingleton(new NoopGraphQLService());
    }

    for (final Object service : services.getServices()) {
      // Spring might have proxied our classes so they won't work with SPQR directly
      final Class<?> userClass = ClassUtils.getUserClass(service);
      factory = factory.withOperationsFromSingleton(service, userClass);
    }

    final GraphQLSchema schema = factory.generate();

    if (log.isTraceEnabled()) {
      final String schemaString = new SchemaPrinter().print(schema);
      log.trace(schemaString);
    }

    return schema;
  }
}
