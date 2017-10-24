package io.committed.vessel.plugins.data.jdbc;


import io.committed.vessel.extensions.VesselDataExtension;

public class JdbcPlugin implements VesselDataExtension {


  @Override
  public Class<?> getConfiguration() {
    return JdbcConfiguration.class;
  }
}
