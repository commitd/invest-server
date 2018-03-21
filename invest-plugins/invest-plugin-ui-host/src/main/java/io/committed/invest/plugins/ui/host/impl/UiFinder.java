package io.committed.invest.plugins.ui.host.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.committed.invest.extensions.collections.InvestExtensions;
import io.committed.invest.plugins.ui.host.UiHostSettings;
import io.committed.invest.plugins.ui.host.data.InvestHostedUiExtensions;
import io.committed.invest.plugins.ui.host.data.PluginJson;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class UiFinder {

  private final UiHostSettings settings;
  private final PluginFinder pluginFinder;

  public UiFinder(final UiHostSettings settings, final PluginFinder pluginFinder) {
    this.settings = settings;
    this.pluginFinder = pluginFinder;
  }

  @Bean
  public InvestHostedUiExtensions pluginJsonUiHostedExtensions() throws IOException {
    return findUiHostedExtensions();
  }

  @Bean
  public InvestExtensions uiHostedExtensions() throws IOException {
    return new InvestExtensions(pluginJsonUiHostedExtensions().getExtensions());
  }

  private InvestHostedUiExtensions findUiHostedExtensions() {
    final List<PluginJson> extensions = settings.getRoots().stream()
        .flatMap(pluginFinder::readPluginsFromDirectory)
        .collect(Collectors.toList());

    return new InvestHostedUiExtensions(extensions);
  }



}
