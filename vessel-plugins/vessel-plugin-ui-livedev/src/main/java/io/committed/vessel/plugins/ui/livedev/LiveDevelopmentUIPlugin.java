package io.committed.vessel.plugins.ui.livedev;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.vessel.extensions.VesselUiExtension;

@Configuration
@Import({ LiveDevelopmentUIConfig.class })
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
    return "code";
  }

  @Override
  public String getStaticResourcePath() {
    // We have no static resource, and want to handle the endpoint ourselves
    return null;
  }
}
