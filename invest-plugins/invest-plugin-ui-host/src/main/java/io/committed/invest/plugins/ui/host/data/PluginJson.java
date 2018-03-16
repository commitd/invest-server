package io.committed.invest.plugins.ui.host.data;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.committed.invest.extensions.InvestUiExtension;
import io.committed.invest.extensions.actions.ActionDefinition;
import io.committed.invest.extensions.actions.SimpleActionDefinition;
import lombok.Data;

@Data
public class PluginJson implements InvestUiExtension {

  private String id;

  private String name = "UI Plugin";

  private String description = "";

  private String icon = "browser";

  private Set<String> roles = Collections.emptySet();

  private Collection<SimpleActionDefinition> actions = Collections.emptyList();

  // TODO; Seperate PluginJson definition from the extension implementation
  @JsonIgnore
  private Resource resource;

  private Map<String, Object> settings;

  @Override
  public String getStaticResourcePath() {
    // TODO: We could abstract this to a resource (in InvestUiExtension) rather than a classpath string
    // Not a static classpath, managed internally
    return null;
  }

  @Override
  public Collection<ActionDefinition> getActions() {
    return actions == null ? Collections.emptyList()
        : actions.stream().map(ActionDefinition.class::cast).collect(Collectors.toList());
  }

  @Override
  public Optional<Map<String, Object>> getSettings() {
    return Optional.ofNullable(settings);
  }

}
