package io.committed.invest.plugins.ui.application;

import io.committed.invest.extensions.graphql.GraphQLService;
import io.leangen.graphql.annotations.GraphQLQuery;

@GraphQLService
public class ApplicationSettingGraphQlResolver {

  private final UiApplicationSettings settings;

  public ApplicationSettingGraphQlResolver(final UiApplicationSettings settings) {
    this.settings = settings;
  }

  @GraphQLQuery(name = "applicationSettings", description = "Configuration for the application")
  public UiApplicationSettings applicationSettings() {
    return settings;

  }
}
