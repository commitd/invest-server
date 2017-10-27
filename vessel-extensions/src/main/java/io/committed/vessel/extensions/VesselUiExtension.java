package io.committed.vessel.extensions;

public interface VesselUiExtension extends VesselExtension {


  default String getStaticResourcePath() {
    return "/static/";
  }

  /**
   * A MAterial UI font icon to use in menu bars etc.
   *
   * @return string (non null)
   */
  default String getIcon() {
    return "add-circle";
  }

}
