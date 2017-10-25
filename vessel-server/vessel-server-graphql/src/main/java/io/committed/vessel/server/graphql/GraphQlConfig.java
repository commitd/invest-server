package io.committed.vessel.server.graphql;

import java.util.Collections;
import java.util.Map;

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
import io.committed.vessel.extensions.graphql.VesselGraphQlService;
import io.committed.vessel.server.graphql.data.VesselGraphQlServices;
import io.committed.vessel.server.graphql.mappers.FluxToCollectionTypeAdapter;
import io.committed.vessel.server.graphql.mappers.MonoAdapter;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.query.PublicResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;

@Configuration
public class GraphQlConfig {

  @Autowired
  private ApplicationContext context;


  @Bean
  public RouterFunction<ServerResponse> graphQlRouterFunction(final GraphQlHandler handler) {
    return RouterFunctions
        .route(RequestPredicates.POST("/graphql")
            .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::postQuery)
        .andRoute(RequestPredicates.GET("/graphql"), handler::getQuery)
        .andRoute(RequestPredicates.GET("/schema.json"), handler::getSchema);
  }

  @Bean
  public GraphQlHandler graphQlHandler(final GraphQLSchema schema, final ObjectMapper mapper) {
    return new GraphQlHandler(schema, mapper);
  }

  @Bean
  public VesselGraphQlServices graphQlServices() {
    final Map<String, Object> beansWithAnnotation =
        context.getBeansWithAnnotation(VesselGraphQlService.class);

    return new VesselGraphQlServices(
        Collections.unmodifiableCollection(beansWithAnnotation.values()));
  }


  @Bean
  public GraphQLSchema schema(final VesselGraphQlServices services) {


    GraphQLSchemaGenerator factory = new GraphQLSchemaGenerator()
        .withDefaults()
        .withResolverBuilders(
            new AnnotatedResolverBuilder(),
            // Resolve public methods everywhere
            // TODO: is this safe... I don't know what else we could do?
            new PublicResolverBuilder(null))
        .withValueMapperFactory(new JacksonValueMapperFactory())
        // Deal with reactive types
        .withTypeAdapters(new MonoAdapter(), new FluxToCollectionTypeAdapter());

    // At least one service must be registered otherwise GraphQL will thrown an exception
    if (services.getServices().isEmpty()) {
      factory = factory.withOperationsFromSingleton(new NoopGraphQLService());
    }

    for (final Object service : services.getServices()) {
      // Spring might have proxied our classes so they won't work with SPQR directly
      final Class<?> userClass = ClassUtils.getUserClass(service);
      factory = factory.withOperationsFromSingleton(service, userClass);
    }

    return factory.generate();

  }

}
