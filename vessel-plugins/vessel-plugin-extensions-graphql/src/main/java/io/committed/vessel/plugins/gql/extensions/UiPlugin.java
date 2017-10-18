package io.committed.vessel.plugins.gql.extensions;

import io.committed.vessel.extensions.VesselUiExtension;
import lombok.Data;

@Data
public class UiPlugin {

  private final String name;
  private final String id;
  private final String description;
  private final String url;
  private final String icon;

  public UiPlugin(final VesselUiExtension extension) {
    this.id = extension.getId();
    this.name = extension.getName();
    this.description = extension.getDescription();
    // TODO this should be provided by a service.. or something equivalent to the
    // ServletRegistrationBean
    this.url = "/ui/" + extension.getId() + "/index.html";
    // TODO
    this.icon = "";

  }
}
