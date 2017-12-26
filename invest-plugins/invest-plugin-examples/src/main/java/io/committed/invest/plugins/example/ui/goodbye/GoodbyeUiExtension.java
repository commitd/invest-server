package io.committed.invest.plugins.example.ui.goodbye;

import org.springframework.stereotype.Component;

import io.committed.invest.extensions.InvestUiExtension;

@Component
public class GoodbyeUiExtension implements InvestUiExtension {

  @Override
  public String getStaticResourcePath() {
    return "/ui-goodbye/";
  }

}
