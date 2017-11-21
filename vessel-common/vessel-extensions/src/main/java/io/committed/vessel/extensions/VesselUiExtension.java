package io.committed.vessel.extensions;

import java.util.Collection;
import java.util.Collections;

import io.committed.vessel.actions.ActionDefinition;

public interface VesselUiExtension extends VesselExtension {


  default String getStaticResourcePath() {
    return String.format("/ui/%s/", getId());
  }

  /**
   * A Material UI font icon to use in menu bars etc.
   *
   * @return string (non null)
   */
  default String getIcon() {
    return "browser";
  }

  default Collection<ActionDefinition> getActions() {
    return Collections.emptyList();
  }

  default Class<?> getSettings() {
    return null;
  }

}
