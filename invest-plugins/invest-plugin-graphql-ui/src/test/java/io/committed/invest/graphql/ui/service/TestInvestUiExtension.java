package io.committed.invest.graphql.ui.service;

import java.util.Collection;
import java.util.Optional;
import io.committed.invest.extensions.InvestUiExtension;
import io.committed.invest.extensions.actions.ActionDefinition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestInvestUiExtension implements InvestUiExtension {

  private String id;
  private String name;
  private String description;
  private Collection<String> roles;
  private Collection<ActionDefinition> actions;
  private Object settings;

  public TestInvestUiExtension(final String id) {
    this.id = id;
    this.name = id.replace("-", " ");
    this.description = this.name + " description";
  }

  @Override
  public Optional<? extends Object> getSettings() {
    return Optional.ofNullable(settings);
  }

  public void setSettings(final Object settings) {
    this.settings = settings;
  }
}
