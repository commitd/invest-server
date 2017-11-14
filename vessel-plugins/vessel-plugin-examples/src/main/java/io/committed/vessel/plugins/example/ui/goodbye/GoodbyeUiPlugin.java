package io.committed.vessel.plugins.example.ui.goodbye;

import org.springframework.stereotype.Component;

import io.committed.vessel.extensions.VesselUiExtension;

@Component
public class GoodbyeUiPlugin implements VesselUiExtension {

  @Override
  public String getStaticResourcePath() {
    return "/ui-goodbye/";
  }

}
