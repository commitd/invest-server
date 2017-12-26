package io.committed.invest.core.services;

import io.committed.invest.extensions.InvestUiExtension;

public interface UiUrlService {

  default String getFullPath(final InvestUiExtension extension) {
    return getContextPath() + getContextRelativePath(extension);
  }

  String getContextRelativePath(InvestUiExtension extension);

  String getContextPath();

  boolean isPathForExtensionRoot(String path);

}
