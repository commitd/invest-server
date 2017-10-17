package io.committed.vessel.plugins.example.graphql;

import io.committed.vessel.extensions.VesselExtension;

public class GraphQlExample implements VesselExtension {

  @Override
  public Class<?> getConfiguration() {
    return GraphQlExampleConfig.class;
  }
}
