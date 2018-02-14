package io.committed.invest.plugins.ui.host;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import io.committed.invest.extensions.InvestExtension;
import io.committed.invest.plugins.ui.host.impl.UiFinder;
import io.committed.invest.plugins.ui.host.impl.UiRouter;

@Configuration
@EnableConfigurationProperties(UiHostSettings.class)
@Import({UiFinder.class, UiRouter.class})
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
