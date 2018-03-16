package io.committed.invest.graphql.ui.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import io.committed.invest.extensions.annotations.GraphQLService;
import io.committed.invest.extensions.graphql.InvestUiNode;
import io.committed.invest.graphql.ui.data.UiActionDefinition;
import io.committed.invest.graphql.ui.data.UiPlugin;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

/**
 * Server side implementation of the InvestUI's local query.
 *
 * These provide a mirror of the Invest UI which can be called if the. They don't necessarily match
 * the exact functionality of the client side, but the implementations are correct in terms of
 * response. An aspect where the UI is superiour might be use of authentication state to filter the
 * results.
 *
 * All functions sit under the {@link InvestUiNode}.
 *
 *
 */
@GraphQLService
public class InvestUiQueryResolver {
  private final AvailablePluginsGraphQlResolver serverResolver;

  @Autowired
  public InvestUiQueryResolver(final AvailablePluginsGraphQlResolver serverResolver) {
    this.serverResolver = serverResolver;

  }

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

    final Flux<PluginActionDefinition> stream = serverResolver.uiPlugins(null)
        .flatMap(p -> p.getActions() == null ? Flux.empty()
            : Flux.fromIterable(p.getActions())
                .doOnNext(a -> System.out.print("a"))
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
  @NoArgsConstructor
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
