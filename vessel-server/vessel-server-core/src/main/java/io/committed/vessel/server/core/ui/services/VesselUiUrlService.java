package io.committed.vessel.server.core.ui.services;

import org.springframework.stereotype.Service;

import io.committed.vessel.core.services.UiUrlService;
import io.committed.vessel.extensions.VesselUiExtension;

@Service
public class VesselUiUrlService implements UiUrlService {

  @Override
  public String getContextRelativePath(final VesselUiExtension extension) {
    return String.format("/%s", extension.getId());
  }

  @Override
  public String getContextPath() {
    return "/ui";
  }



}
