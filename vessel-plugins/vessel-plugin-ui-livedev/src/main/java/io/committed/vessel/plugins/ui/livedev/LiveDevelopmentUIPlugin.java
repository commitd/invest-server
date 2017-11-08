package io.committed.vessel.plugins.ui.livedev;

import io.committed.vessel.extensions.VesselUiExtension;

public class LiveDevelopmentUIPlugin implements VesselUiExtension {

  @Override
  public String getName() {
    return "Live Development";
  }

  @Override
  public String getDescription() {
    return "Support live coding of a UI plugins with automatic reloading";
  }

  @Override
  public String getIcon() {
    return "build";
  }

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
