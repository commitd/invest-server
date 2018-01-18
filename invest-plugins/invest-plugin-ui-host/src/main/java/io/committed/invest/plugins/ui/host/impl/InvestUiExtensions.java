package io.committed.invest.plugins.ui.host.impl;

import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InvestUiExtensions extends InvestExtensions<PluginJson> {

  public InvestUiExtensions(final Collection<PluginJson> extensions) {
    super(extensions);
  }


}
