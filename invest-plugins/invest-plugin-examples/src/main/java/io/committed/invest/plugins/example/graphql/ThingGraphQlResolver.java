package io.committed.invest.plugins.example.graphql;

import java.util.Arrays;
import io.committed.invest.extensions.annotations.GraphQLService;
import io.leangen.graphql.annotations.GraphQLQuery;
import reactor.core.publisher.Flux;

@GraphQLService
public class ThingGraphQlResolver {

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

  @GraphQLQuery(name = "things", description = "Example provider of things")
  public Flux<Thing> getThings() {
    return Flux.fromIterable(Arrays.asList(new Thing("hello")));
  }
}
