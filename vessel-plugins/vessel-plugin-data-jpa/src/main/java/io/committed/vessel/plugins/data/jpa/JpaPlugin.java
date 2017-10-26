package io.committed.vessel.plugins.data.jpa;


import io.committed.vessel.extensions.VesselDataExtension;

public class JpaPlugin implements VesselDataExtension {


  @Override
  public Class<?> getConfiguration() {
    return JpaConfiguration.class;
  }
}
