package io.committed.invest.plugins.ui.host.impl;

import java.util.Collection;

public class InvestHostedUiExtensions {

  private final Collection<PluginJson> extensions;

  public InvestHostedUiExtensions(final Collection<PluginJson> extensions) {
    this.extensions = extensions;
  }

  public Collection<PluginJson> getExtensions() {
    return extensions;
  }

  public boolean isEmpty() {
    return extensions == null || extensions.isEmpty();
  }

}
