package io.committed.invest.plugins.ui.actiondev;

import org.springframework.context.annotation.Configuration;
import io.committed.invest.extensions.InvestUiExtension;

@Configuration
public class ActionDevelopmentUIExtension implements InvestUiExtension {


  @Override
  public String getId() {
    return "dev-action";
  }

  @Override
  public String getName() {
    return "Action Development";
  }

  @Override
  public String getDescription() {
    return "Send actions to plugins";
  }

  @Override
  public String getIcon() {
    return "code";
  }

}