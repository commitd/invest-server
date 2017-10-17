package io.committed.vessel.plugins.example.graphql;

import java.util.Arrays;
import java.util.stream.Stream;

import io.committed.vessel.extensions.graphql.VesselGraphQlService;
import io.leangen.graphql.annotations.GraphQLQuery;

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
  }

  @GraphQLQuery(name = "things")
  public Stream<Thing> getThings() {
    return Arrays.asList(new Thing("hello")).stream();
  }
}
