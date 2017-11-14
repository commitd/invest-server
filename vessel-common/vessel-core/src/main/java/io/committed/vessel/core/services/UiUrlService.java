package io.committed.vessel.core.services;

import io.committed.vessel.extensions.VesselUiExtension;

public interface UiUrlService {

  default String getFullPath(final VesselUiExtension extension) {
    return getContextPath() + getContextRelativePath(extension);
  }

  String getContextRelativePath(VesselUiExtension extension);

  String getContextPath();

  boolean isPathForExtensionRoot(String path);

}
