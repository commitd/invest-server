package io.committed.vessel.plugins.example.graphql;

import java.util.Arrays;

import org.springframework.security.access.prepost.PreAuthorize;

import io.committed.vessel.extensions.graphql.VesselGraphQlService;
import io.leangen.graphql.annotations.GraphQLQuery;
import reactor.core.publisher.Flux;

@VesselGraphQlService
public class ThingService {

  public static final class Thing {
    private final String name;

    public Thing(final String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public String getPassword() {
      return "secret";
    }
  }

  @GraphQLQuery(name = "things")
  @PreAuthorize("hasRole('USER')")
  public Flux<Thing> getThings() {
    return Flux.fromIterable(Arrays.asList(new Thing("hello")));
  }
}
