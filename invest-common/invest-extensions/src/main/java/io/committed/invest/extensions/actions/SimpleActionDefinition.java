package io.committed.invest.extensions.actions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** A simple POJO based implementation of Action defintion, with builder. */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleActionDefinition implements ActionDefinition {

  /** The action. */
  private String action;

  /** The payload. */
  private Class<?> payload;

  /** The title. */
  private String title;

  /** The description. */
  private String description;
}
