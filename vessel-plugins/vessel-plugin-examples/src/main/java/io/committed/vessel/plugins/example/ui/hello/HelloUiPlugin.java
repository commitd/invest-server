package io.committed.vessel.plugins.example.ui.hello;

import io.committed.vessel.server.extensions.VesselUiExtension;

public class HelloUiPlugin implements VesselUiExtension {

  @Override
  public String getStaticResourcePath() {
    return "/ui-hello/";
  }

}
