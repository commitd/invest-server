package io.committed.invest.graphql.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import io.committed.invest.extensions.annotations.GraphQLService;
import io.committed.invest.extensions.graphql.InvestServerNode;
import io.committed.invest.extensions.graphql.InvestUiNode;
import io.leangen.graphql.annotations.GraphQLQuery;

/**
 * Mount the {@link InvestServerNode} and {@link InvestUiNode} into the GraphQL schema so other can
 * use them as Context.
 */
@GraphQLService
public class ServerRootGraphQlResolver {

  private final InvestServerNode serverNode;
  private final InvestUiNode uiNode;

  @Autowired
  public ServerRootGraphQlResolver() {
    this.serverNode = new InvestServerNode();
    this.uiNode = new InvestUiNode();
  }

  @GraphQLQuery(name = "investServer",
      description = "Root for all Invest Server queries and endpoints")
  public InvestServerNode server() {
    return serverNode;
  }


  @GraphQLQuery(name = "remoteInvestUi",
      description = "Mirrors the application's ui endpoint, allowing for testing of plugins withouth the Outer Frame")
  public InvestUiNode investUiQuery() {
    return uiNode;
  }

}
