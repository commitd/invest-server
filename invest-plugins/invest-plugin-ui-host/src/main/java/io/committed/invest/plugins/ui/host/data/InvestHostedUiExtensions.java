package io.committed.invest.plugins.ui.host.data;

import java.util.Collection;
import lombok.Data;

@Data
public class InvestHostedUiExtensions {

  private final Collection<PluginJson> extensions;

  public InvestHostedUiExtensions(final Collection<PluginJson> extensions) {
    this.extensions = extensions;
  }

  public boolean isEmpty() {
    return extensions == null || extensions.isEmpty();
  }

}
