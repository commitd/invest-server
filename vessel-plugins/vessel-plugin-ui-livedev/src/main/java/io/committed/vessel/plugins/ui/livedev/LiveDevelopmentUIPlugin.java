package io.committed.vessel.plugins.ui.livedev;

import io.committed.vessel.extensions.VesselUiExtension;

public class LiveDevelopmentUIPlugin implements VesselUiExtension {

  @Override
  public Class<?> getConfiguration() {
    return LiveDevelopmentUIConfig.class;
  }

  @Override
  public String getStaticResourcePath() {
    // We have no static resource, and want to handle the endpoint ourselves
    return null;
  }
}
