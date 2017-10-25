package io.committed.vessel.plugin.server.auth;

import io.committed.vessel.extensions.VesselExtension;

public class AuthPlugin implements VesselExtension {

  @Override
  public Class<?> getConfiguration() {
    return AuthConfiguration.class;
  }
}
