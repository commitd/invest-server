package io.committed.invest.plugins.ui.host.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.committed.invest.plugins.ui.host.UiHostSettings;
import io.committed.invest.plugins.ui.host.data.PluginJson;

@Service
public class PluginSettingsMerger {

  private final UiHostSettings settings;

  @Autowired
  public PluginSettingsMerger(final UiHostSettings settings) {
    this.settings = settings;
  }

  public PluginJson merge(final PluginJson json) {
    final Optional<Map<String, Object>> overriddenSettings = settings.getSettingsForPluginId(json.getId());

    if (!overriddenSettings.isPresent()) {
      return json;
    }

    final Optional<Map<String, Object>> jsonSettings = json.getSettings();
    if (!jsonSettings.isPresent()) {
      json.setSettings(overriddenSettings.get());
      return json;
    }

    // We need to merge the settings.. we go invest.json then settings
    // NOTE: this isn't a deep merge (that seems dangerous)
    final Map<String, Object> merged = new HashMap<>();
    merged.putAll(jsonSettings.get());
    merged.putAll(overriddenSettings.get());
    json.setSettings(merged);

    return json;
  }
}
