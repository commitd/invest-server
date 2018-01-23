package io.committed.invest.graphql.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import io.committed.invest.extensions.annotations.GraphQLService;
import io.leangen.graphql.annotations.GraphQLQuery;

@GraphQLService
public class ServerRootGraphQlResolver {

  private final ServerGraphQlResolver serverResolver;
  private final OuterFrameUiResolver uiResolver;

  @Autowired
  public ServerRootGraphQlResolver(final ServerGraphQlResolver service, final OuterFrameUiResolver uiResolver) {
    this.serverResolver = service;
    this.uiResolver = uiResolver;
  }

  @GraphQLQuery(name = "investServer",
      description = "Root for all Invest Server queries and endpoints")
  public ServerGraphQlResolver server() {
    return serverResolver;
  }


  @GraphQLQuery(name = "investUi",
      description = "Mirrors the application's ui endpoint, allowing for testing of plugins withouth the Outer Frame")
  public OuterFrameUiResolver outerFrameUi() {
    return uiResolver;
  }
}
