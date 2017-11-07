package io.committed.vessel.actions;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SimpleActionDefinition implements ActionDefinition {

  private final String action;

  private final Class<?> payload;

  private final String title;

  private final String description;


}
