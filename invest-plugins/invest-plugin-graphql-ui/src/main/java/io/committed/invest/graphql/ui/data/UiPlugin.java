package io.committed.invest.graphql.ui.data;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.committed.invest.extensions.InvestUiExtension;

/** DTO for UI plugins */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UiPlugin {

  private String id;
  private String name;
  private String description;
  private String url;
  private String icon;
  private Collection<String> roles;
  private List<UiActionDefinition> actions;

  public UiPlugin(final InvestUiExtension extension, final String url) {
    this.id = extension.getId();
    this.name = extension.getName();
    this.description = extension.getDescription();
    this.url = url;
    this.icon = extension.getIcon();
    this.actions =
        extension.getActions() != null
            ? extension
                .getActions()
                .stream()
                .map(UiActionDefinition::new)
                .collect(Collectors.toList())
            : Collections.emptyList();
    this.roles = extension.getRoles();
    if (roles == null) {
      this.roles = Collections.emptyList();
    }
  }
}
