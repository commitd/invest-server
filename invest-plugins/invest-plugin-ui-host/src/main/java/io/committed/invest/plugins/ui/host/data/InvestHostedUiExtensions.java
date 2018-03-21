package io.committed.invest.plugins.ui.host.data;

import java.util.Collection;
import java.util.Collections;
import lombok.Data;

@Data
public class InvestHostedUiExtensions {

  private final Collection<PluginJson> extensions;

  public InvestHostedUiExtensions(final Collection<PluginJson> extensions) {
    if (extensions != null) {
      this.extensions = Collections.unmodifiableCollection(extensions);
    } else {
      this.extensions = Collections.emptyList();
    }
  }

  public boolean isEmpty() {
    return extensions == null || extensions.isEmpty();
  }

}
