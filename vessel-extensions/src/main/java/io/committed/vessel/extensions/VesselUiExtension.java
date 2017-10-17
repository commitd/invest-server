package io.committed.vessel.extensions;

public interface VesselUiExtension extends VesselExtension {


  default String getStaticResourcePath() {
    return "/static/";
  }

}
