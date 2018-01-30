package io.committed.invest.graphql.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import io.committed.invest.extensions.annotations.GraphQLService;
import io.leangen.graphql.annotations.GraphQLQuery;

@GraphQLService
public class ServerRootGraphQlResolver {

  private final ServerGraphQlResolver serverResolver;
  private final InvestUiNode uiNode;

  @Autowired
  public ServerRootGraphQlResolver(final ServerGraphQlResolver service) {
    this.serverResolver = service;
    this.uiNode = new InvestUiNode();

  }

  @GraphQLQuery(name = "investServer",
      description = "Root for all Invest Server queries and endpoints")
  public ServerGraphQlResolver server() {
    return serverResolver;
  }


  @GraphQLQuery(name = "remoteInvestUi",
      description = "Mirrors the application's ui endpoint, allowing for testing of plugins withouth the Outer Frame")
  public InvestUiNode investUiQuery() {
    return uiNode;
  }

}
