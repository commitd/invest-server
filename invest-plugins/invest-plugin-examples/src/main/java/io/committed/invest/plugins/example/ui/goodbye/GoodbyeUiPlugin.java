package io.committed.invest.plugins.example.ui.goodbye;

import org.springframework.stereotype.Component;

import io.committed.invest.extensions.VesselUiExtension;

@Component
public class GoodbyeUiPlugin implements VesselUiExtension {

  @Override
  public String getStaticResourcePath() {
    return "/ui-goodbye/";
  }

}
