package io.committed.invest.server.graphql;

import io.leangen.graphql.annotations.GraphQLQuery;

// Do not annotate this, it is added programmatically
public class NoopGraphQLService {

  @GraphQLQuery(name = "empty")
  public String empty() {
    return "Add some GraphQL service plugins";
  }
}
