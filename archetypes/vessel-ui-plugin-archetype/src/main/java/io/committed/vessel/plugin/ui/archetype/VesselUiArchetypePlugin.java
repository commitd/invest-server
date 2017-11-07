package io.committed.vessel.plugin.ui.archetype;

import io.committed.vessel.extensions.VesselUiExtension;

/**
 * Extension point for VesselUiArchetype.
 * 
 */
public class VesselUiArchetypePlugin implements VesselUiExtension {

  @Override
  public String getId() {
    return "VesselUiArchetype";
  }

  @Override
  public Class<?> getSettings() {
    return VesselUiArchetypeSettings.class;
  }

  // TODO: You should override to provide additional information such name, description and logo

  @Override
  public String getStaticResourcePath() {
    // Do not change this without also changing the pom.xml copy-resources
    // as Maven will copy the output from the JS build into this location
    return "/ui/VesselUiArchetype/";
  }
}
