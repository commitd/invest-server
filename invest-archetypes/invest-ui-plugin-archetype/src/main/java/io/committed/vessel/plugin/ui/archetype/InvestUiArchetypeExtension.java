package io.committed.vessel.plugin.ui.archetype;

import io.committed.invest.extensions.InvestUiExtension;

/**
 * Extension point for ImpactUiArchetype.
 *
 */
public class InvestUiArchetypeExtension implements InvestUiExtension {

  @Override
  public String getId() {
    return "ImpactUiArchetype";
  }

  @Override
  public Class<?> getSettings() {
    return InvestUiArchetypeSettings.class;
  }

  // TODO: You should override to provide additional information such name, description and logo

  @Override
  public String getStaticResourcePath() {
    // Do not change this without also changing the pom.xml copy-resources
    // as Maven will copy the output from the JS build into this location
    return "/ui/ImpactUiArchetype/";
  }
}
