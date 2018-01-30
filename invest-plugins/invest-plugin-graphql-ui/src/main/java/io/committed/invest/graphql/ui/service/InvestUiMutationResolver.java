package io.committed.invest.graphql.ui.service;

import io.committed.invest.extensions.annotations.GraphQLService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@GraphQLService
@Slf4j
public class InvestUiMutationResolver {

  // Mutations

  @GraphQLMutation(name = "remoteNavigateToPlugin")
  public NavigateOutput navigate(@GraphQLArgument(name = "input") final Navigate input) {
    log.info("Request to navigate from UI ignored");
    return new NavigateOutput(false);
  }


  @Data
  @AllArgsConstructor
  public static final class Navigate {

    @GraphQLQuery(name = "pluginId")
    @GraphQLNonNull
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
