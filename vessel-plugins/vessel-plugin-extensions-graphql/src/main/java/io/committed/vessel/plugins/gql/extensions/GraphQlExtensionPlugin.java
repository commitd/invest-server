package io.committed.vessel.plugins.gql.extensions;

import io.committed.vessel.extensions.VesselGraphQlExtension;

public class GraphQlExtensionPlugin implements VesselGraphQlExtension {

  @Override
  public Class<?> getConfiguration() {
    return VesselGraphQlExtensionConfig.class;
  }
}
