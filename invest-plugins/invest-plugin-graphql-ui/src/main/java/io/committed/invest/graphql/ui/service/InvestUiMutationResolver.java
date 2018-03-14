package io.committed.invest.graphql.ui.service;

import io.committed.invest.extensions.annotations.GraphQLService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Server side implementation UI's local GraphQL functions.
 *
 * This is only useful as a stub that a development UI can hit. We can't really perform the actions
 * or deal with UI state properly but at
 *
 * Note the queries names are slightly different (if nothing else they have a PREFIX). ideally this
 * won't be the case, but we sound on the UI side the schema stitching would always default to the
 * server implementation if present (which is the less desirable of the two).
 *
 *
 */
@GraphQLService
@Slf4j
public class InvestUiMutationResolver {

  private static final String PREFIX = "remote";

  @GraphQLMutation(name = PREFIX + "NavigateToPlugin")
  public NavigateOutput navigate(@GraphQLArgument(name = "input") final Navigate input) {
    log.info("Request to navigate from UI ignored");
    return new NavigateOutput(false);
  }


  @Data
  @NoArgsConstructor
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
