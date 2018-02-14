package io.committed.invest.graphql.ui.data;

import io.committed.invest.extensions.actions.ActionDefinition;
import lombok.Data;

@Data
public class UiActionDefinition {

  private String action;
  private String title;
  private String description;

  public UiActionDefinition(final ActionDefinition defn) {
    this.action = defn.getAction();
    this.description = defn.getDescription();
    this.title = defn.getTitle();
    // TODO: Ignoring payload, but this should be converted to something more JSON meaninful (eg
    // flow defn)
  }

}
