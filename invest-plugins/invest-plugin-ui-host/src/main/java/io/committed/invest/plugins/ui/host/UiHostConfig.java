package io.committed.invest.plugins.ui.host;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.committed.invest.plugins.ui.host.impl.PluginFinder;
import io.committed.invest.plugins.ui.host.impl.PluginJsonReader;
import io.committed.invest.plugins.ui.host.impl.PluginSettingsMerger;
import io.committed.invest.plugins.ui.host.impl.PluginZipReader;
import io.committed.invest.plugins.ui.host.impl.UiFinder;
import io.committed.invest.plugins.ui.host.impl.UiRouter;

/** Configuration bean to wired up services in this extension */
@Configuration
@Import({UiRouter.class, UiFinder.class})
public class UiHostConfig {

  @Bean
  public PluginFinder uiHostPluginFinder(
      final UiHostSettings settings,
      final PluginZipReader zipReader,
      final PluginJsonReader jsonReader,
      final PluginSettingsMerger settingsMerger) {
    return new PluginFinder(settings, zipReader, jsonReader, settingsMerger);
  }

  @Bean
  public PluginJsonReader uiHostPluginJsonReader(final ObjectMapper mapper) {
    return new PluginJsonReader(mapper);
  }

  @Bean
  public PluginZipReader uiHostPluginZipReader(final ObjectMapper mapper) {
    return new PluginZipReader(mapper);
  }

  @Bean
  public PluginSettingsMerger uiHostPLuginSettingMerger(final UiHostSettings settings) {
    return new PluginSettingsMerger(settings);
  }
}
