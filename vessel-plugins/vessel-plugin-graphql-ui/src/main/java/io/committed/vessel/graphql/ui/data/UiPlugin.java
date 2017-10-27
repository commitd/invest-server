package io.committed.vessel.graphql.ui.data;

import io.committed.vessel.extensions.VesselUiExtension;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UiPlugin {

  private String id;
  private String name;
  private String description;
  private String url;

  public UiPlugin(final VesselUiExtension extension, final String url) {
    this.id = extension.getId();
    this.name = extension.getName();
    this.description = extension.getDescription();
    this.url = url;
  }
}
