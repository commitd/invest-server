package io.committed.invest.plugins.ui.host;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

import io.committed.invest.plugins.ui.host.data.PluginOverride;

/**
 * Settings for the UI Host extension
 *
 * <p>The override allows you to override the settings in the invest.json.
 *
 * <pre>
 * invest:
 *   ui:
 *     host:
 *       override:
 *        - id: plugin-id
 *          settings:
 *            settingkey: settingvalue
 * </pre>
 */
@ConfigurationProperties("invest.ui.host")
@Data
public class UiHostSettings {
  private List<File> roots = Collections.singletonList(new File("./ui"));

  private String pluginFilename = "invest.json";

  private List<PluginOverride> override;

  public Optional<Map<String, Object>> getSettingsForPluginId(final String pluginId) {
    if (override == null) {
      return Optional.empty();
    }

    return override
        .stream()
        .filter(s -> s.getId().equals(pluginId))
        .findFirst()
        .map(PluginOverride::getSettings);
  }
}
