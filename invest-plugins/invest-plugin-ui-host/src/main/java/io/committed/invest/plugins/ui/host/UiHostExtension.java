package io.committed.invest.plugins.ui.host;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import io.committed.invest.extensions.InvestExtension;

/**
 * Extension which searchs for non-Java UI plugins (invest.json with index.html) on the filesystem
 * and mounts them into the UI.
 *
 */
@Configuration
@EnableConfigurationProperties(UiHostSettings.class)
@Import(value = {UiHostConfig.class})
public class UiHostExtension implements InvestExtension {

  @Override
  public String getId() {
    return "uihost";
  }

  @Override
  public String getName() {
    return "UI Host";
  }

  @Override
  public String getDescription() {
    return "Registers UI plugins from the file system";
  }

}
