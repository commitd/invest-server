package io.committed.invest.server.core.services;

import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import io.committed.invest.core.services.UiUrlService;
import io.committed.invest.extensions.InvestUiExtension;

@Service
public class SimpleUiUrlService implements UiUrlService {


  private final Pattern rootPattern =
      Pattern.compile(String.format("%s/[^/]+/?", getContextPath()));

  @Override
  public String getContextRelativePath(final InvestUiExtension extension) {
    return String.format("/%s/", extension.getId());
  }

  @Override
  public String getContextPath() {
    return "/ui";
  }

  @Override
  public boolean isPathForExtensionRoot(final String path) {
    return rootPattern.matcher(path).matches();
  }



}
