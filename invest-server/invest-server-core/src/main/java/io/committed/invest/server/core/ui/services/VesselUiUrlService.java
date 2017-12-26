package io.committed.invest.server.core.ui.services;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import io.committed.invest.core.services.UiUrlService;
import io.committed.invest.extensions.VesselUiExtension;

@Service
public class VesselUiUrlService implements UiUrlService {


  private final Pattern rootPattern =
      Pattern.compile(String.format("%s/[^/]+/?", getContextPath()));

  @Override
  public String getContextRelativePath(final VesselUiExtension extension) {
    return String.format("/%s/", extension.getId());
  }

  // TODO: This could be configurable though perhaps that's dangerous!
  @Override
  public String getContextPath() {
    return "/ui";
  }

  @Override
  public boolean isPathForExtensionRoot(final String path) {
    return rootPattern.matcher(path).matches();
  }



}
