package io.committed.invest.plugins.ui.graphql;

import java.util.Collection;
import java.util.Collections;
import io.committed.invest.core.auth.InvestRoles;
import io.committed.invest.extensions.InvestUiExtension;

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
  public Collection<String> getRoles() {
    return Collections.singleton(InvestRoles.DEV);
  }

  @Override
  public String getStaticResourcePath() {
    return "/ui-graphql/";
  }
}
