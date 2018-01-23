package io.committed.invest.graphql.ui.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import io.committed.invest.extensions.annotations.GraphQLService;
import io.committed.invest.graphql.ui.data.UiActionDefinition;
import io.committed.invest.graphql.ui.data.UiPlugin;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 *
 * We use (in effect) the same GraphQL endpoint (investServer) to satisty this implementation as the
 * UI would.
 *
 *
 * NOTE: This should mirror the functionality available on the UI as best it can. And impleemnt the
 * entireity of investUi as per LocalSchema.ts in invest-framework. However some things will not be
 * possible (eg navigation). These should not fail, but simple do nothing.
 *
 *
 *
 */
@GraphQLService
@Slf4j
public class OuterFrameUiResolver {

  private final ServerGraphQlResolver serverResolver;

  @Autowired
  public OuterFrameUiResolver(final ServerGraphQlResolver serverResolver) {
    this.serverResolver = serverResolver;

  }

  // Queries

  @GraphQLQuery(name = "status")
  public String status() {
    return "ok";
  }

  @GraphQLQuery(name = "actions")
  public QueryActionOutput actions(@GraphQLArgument(name = "input") final QueryActionInput input) {
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

  // Mutations

  @GraphQLMutation(name = "navigate")
  public NavigateOutput navigate(@GraphQLArgument(name = "input") final NavigateInput input) {
    log.info("Request to navigate from UI ignored");
    return new NavigateOutput(false);
  }


  @Data
  @AllArgsConstructor
  public static final class NavigateInput {
    private String pluginId;
    private String action;
    private String payload;
  }

  @Data
  @AllArgsConstructor
  public static final class NavigateOutput {
    private boolean success;
  }
}
