package io.committed.invest.graphql.ui.data;

import io.committed.invest.extensions.actions.ActionDefinition;
import lombok.Data;

/**
 * DTO for Ui actions
 *
 */
@Data
public class UiActionDefinition {

  private String action;
  private String title;
  private String description;

  public UiActionDefinition(final ActionDefinition defn) {
    this.action = defn.getAction();
    this.description = defn.getDescription();
    this.title = defn.getTitle();
  }

}
