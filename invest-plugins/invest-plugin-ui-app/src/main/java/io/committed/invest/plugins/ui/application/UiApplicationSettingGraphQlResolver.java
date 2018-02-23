package io.committed.invest.plugins.ui.application;

import io.committed.invest.extensions.annotations.GraphQLService;
import io.leangen.graphql.annotations.GraphQLQuery;

@GraphQLService
public class UiApplicationSettingGraphQlResolver {

  private final UiApplicationSettings settings;

  public UiApplicationSettingGraphQlResolver(final UiApplicationSettings settings) {
    this.settings = settings;
  }

  @GraphQLQuery(name = "applicationSettings", description = "Configuration for the application")
  public UiApplicationSettings applicationSettings() {
    return settings;
  }
}
