package io.committed.invest.server.graphql;

import io.leangen.graphql.annotations.GraphQLQuery;

/**
 * An empty GraphQL query which acts as a placeholder.
 *
 * <p>This is added by the GrqphQL implementation if no other queries have been defined/found in the
 * application.
 */
// NOTE: No need to annotate this with GraphQLService, it is added programmatically if needed.
public class NoopGraphQLService {

  @GraphQLQuery(name = "empty")
  public String empty() {
    return "Add some GraphQL service plugins";
  }
}
