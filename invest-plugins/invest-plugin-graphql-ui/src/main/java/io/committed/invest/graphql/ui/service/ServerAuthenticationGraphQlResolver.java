package io.committed.invest.graphql.ui.service;

import io.committed.invest.core.auth.AuthenticationSettings;
import io.committed.invest.extensions.annotations.GraphQLService;
import io.committed.invest.extensions.graphql.InvestServerNode;

import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

/** GraphQL resolver to provider authentication settings to the UI */
@GraphQLService
public class ServerAuthenticationGraphQlResolver {
  private final AuthenticationSettings settings;

  public ServerAuthenticationGraphQlResolver(final AuthenticationSettings settings) {
    this.settings = settings;
  }

  @GraphQLQuery(name = "authentication")
  public AuthenticationSettings authentication(@GraphQLContext final InvestServerNode serverNode) {
    return settings;
  }
}
