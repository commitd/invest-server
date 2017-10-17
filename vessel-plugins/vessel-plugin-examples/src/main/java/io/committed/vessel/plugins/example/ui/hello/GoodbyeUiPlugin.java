package io.committed.vessel.plugins.example.ui.hello;

import io.committed.vessel.extensions.VesselUiExtension;

public class GoodbyeUiPlugin implements VesselUiExtension {

  @Override
  public String getStaticResourcePath() {
    return "/ui-goodbye/";
  }

}
