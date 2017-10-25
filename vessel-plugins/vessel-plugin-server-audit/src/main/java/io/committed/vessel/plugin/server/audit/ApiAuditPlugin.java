package io.committed.vessel.plugin.server.audit;

import io.committed.vessel.extensions.VesselExtension;

public class ApiAuditPlugin implements VesselExtension {


  @Override
  public Class<?> getConfiguration() {
    return ApiAuditConfig.class;
  }


}
