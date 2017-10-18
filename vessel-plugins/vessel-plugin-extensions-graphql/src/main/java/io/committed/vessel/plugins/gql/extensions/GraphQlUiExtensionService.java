package io.committed.vessel.plugins.gql.extensions;

import java.util.List;
import java.util.stream.Stream;

import io.committed.vessel.extensions.VesselUiExtension;
import io.committed.vessel.extensions.graphql.VesselGraphQlService;
import io.leangen.graphql.annotations.GraphQLQuery;

@VesselGraphQlService
public class GraphQlUiExtensionService {

  private final List<VesselUiExtension> extensions;

  public GraphQlUiExtensionService(final List<VesselUiExtension> extensions) {
    this.extensions = extensions;
  }

  @GraphQLQuery(name = "plugins")
  public Plugins getPlugins() {
    return new Plugins();
  }

  public class Plugins {

    @GraphQLQuery(name = "ui")
    public Stream<UiPlugin> getUiPlugins() {
      return extensions.stream().map(UiPlugin::new);
    }

  }

}
