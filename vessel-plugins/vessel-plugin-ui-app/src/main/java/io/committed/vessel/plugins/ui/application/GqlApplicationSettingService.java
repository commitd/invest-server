package io.committed.vessel.plugins.ui.application;

import io.committed.vessel.extensions.graphql.VesselGraphQlService;
import io.leangen.graphql.annotations.GraphQLQuery;

@VesselGraphQlService
public class GqlApplicationSettingService {

  private final VesselUiApplicationSettings settings;

  public GqlApplicationSettingService(final VesselUiApplicationSettings settings) {
    this.settings = settings;
  }

  @GraphQLQuery(name = "applicationSettings")
  public VesselUiApplicationSettings applicationSettings() {
    return settings;

  }
}
