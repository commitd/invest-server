package io.committed.invest.plugins.ui.host.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.springframework.core.io.Resource;
import io.committed.invest.actions.ActionDefinition;
import io.committed.invest.extensions.InvestUiExtension;
import lombok.Data;

/**
 * This class mirrors (but does not implement) the {@link InvestUiExtension} *
 */
@Data
// TODO; Seperate PluginJson definition from the extension implementation
public class PluginJson implements InvestUiExtension {

  private String id;

  private String name = "UI Plugin";

  private String description = "";

  private String icon = "browser";

  private Set<String> roles = Collections.emptySet();

  private Collection<ActionDefinition> actions = Collections.emptyList();

  private Resource resource;

  @Override
  public String getStaticResourcePath() {
    // Not a static classpath, managed internally
    return null;
  }

}
