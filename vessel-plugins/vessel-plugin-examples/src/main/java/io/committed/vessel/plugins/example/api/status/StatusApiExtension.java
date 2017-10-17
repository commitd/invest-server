package io.committed.vessel.plugins.example.api.status;

import io.committed.vessel.server.extensions.VesselApiExtension;

public class StatusApiExtension implements VesselApiExtension {


  @Override
  public Class<?> getConfiguration() {
    return StatusApiConfiguration.class;
  }

}
