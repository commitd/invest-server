package io.committed.invest.plugins.ui.graphql;

import io.committed.invest.extensions.InvestUiExtension;

/** Extension which provides a hosted version of the GraphiQL user interface */
public class GraphiQlUiExtension implements InvestUiExtension {

  @Override
  public String getId() {
    return "graphiql";
  }

  @Override
  public String getName() {
    return "GraphiQL";
  }

  @Override
  public String getDescription() {
    return "GraphQL query explorer";
  }

  @Override
  public String getIcon() {
    return "database";
  }

  @Override
  public String getStaticResourcePath() {
    return "/ui-graphql/";
  }
}
