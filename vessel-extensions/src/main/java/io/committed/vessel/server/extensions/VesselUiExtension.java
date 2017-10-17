package io.committed.vessel.server.extensions;

public interface VesselUiExtension extends VesselExtension {


  default String getStaticResourcePath() {
    return "/static/";
  }

}
