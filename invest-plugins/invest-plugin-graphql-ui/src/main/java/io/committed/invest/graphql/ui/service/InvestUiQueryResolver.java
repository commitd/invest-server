package io.committed.invest.graphql.ui.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import io.committed.invest.extensions.annotations.GraphQLService;
import io.committed.invest.graphql.ui.data.UiActionDefinition;
import io.committed.invest.graphql.ui.data.UiPlugin;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

@GraphQLService
public class InvestUiQueryResolver {
  private final ServerGraphQlResolver serverResolver;

  @Autowired
  public InvestUiQueryResolver(final ServerGraphQlResolver serverResolver) {
    this.serverResolver = serverResolver;

  }

  // Queries

  @GraphQLQuery(name = "status")
  public String status(@GraphQLContext final InvestUiNode node) {
    return "ok";
  }

  @GraphQLQuery(name = "actions")
  public QueryActionOutput actions(@GraphQLContext final InvestUiNode node,
      @GraphQLArgument(name = "input") final QueryActionInput input) {
    // If we don't have any args, return empty
    if (input == null || Strings.isEmpty(input.getAction())) {
      return new QueryActionOutput();
    }

    final Flux<PluginActionDefinition> stream = serverResolver.uiPlugins()
        .flatMap(p -> Flux.fromStream(p.getActions())
            .filter(a -> a.getAction().equalsIgnoreCase(input.getAction()))
            .map(a -> new PluginActionDefinition(p, a)));
    return new QueryActionOutput(stream);
  }

  @Data
  public static final class QueryActionInput {
    private String action;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static final class QueryActionOutput {
    private Flux<PluginActionDefinition> definitions = Flux.empty();

  }

  @Data
  public static final class PluginActionDefinition {
    private String pluginId;
    private String title;
    private String description;
    private String action;

    public PluginActionDefinition(final UiPlugin p, final UiActionDefinition a) {
      this.pluginId = p.getId();
      this.title = a.getTitle();
      this.description = a.getDescription();
      this.action = a.getAction();
    }
  }



}
