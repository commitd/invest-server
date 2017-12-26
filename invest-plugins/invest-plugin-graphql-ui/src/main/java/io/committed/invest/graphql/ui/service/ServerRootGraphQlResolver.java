package io.committed.invest.graphql.ui.service;

import org.springframework.beans.factory.annotation.Autowired;

import io.committed.invest.extensions.graphql.GraphQLService;
import io.leangen.graphql.annotations.GraphQLQuery;

@GraphQLService
public class ServerRootGraphQlResolver {

  private final ServerGraphQlResolver service;

  @Autowired
  public ServerRootGraphQlResolver(final ServerGraphQlResolver service) {
    this.service = service;
  }

  @GraphQLQuery(name = "investServer",
      description = "Root for all Invest Server queries and endpoints")
  public ServerGraphQlResolver server() {
    return service;
  }

}
