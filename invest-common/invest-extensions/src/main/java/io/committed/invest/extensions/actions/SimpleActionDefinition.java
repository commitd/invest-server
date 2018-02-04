package io.committed.invest.extensions.actions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleActionDefinition implements ActionDefinition {

  private String action;

  private Class<?> payload;

  private String title;

  private String description;


}
