package io.committed.invest.plugins.ui.host.impl;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.committed.invest.plugins.ui.host.UiHostSettings;
import io.committed.invest.plugins.ui.host.data.PluginJson;
import io.committed.invest.plugins.ui.host.data.PluginOverride;

public class PluginFinderTest {

  private File readFileFromResources(String directory) throws URISyntaxException {
    final ClassLoader classLoader = getClass().getClassLoader();
    URL url = classLoader.getResource("plugins");
    return Paths.get(url.toURI()).toFile();
  }

  @Test
  public void test() throws URISyntaxException {
    final UiHostSettings settings = new UiHostSettings();

    final File file = readFileFromResources("plugins");

    final ObjectMapper mapper = new ObjectMapper();
    final PluginZipReader zipReader = new PluginZipReader(mapper);
    final PluginJsonReader jsonReader = new PluginJsonReader(mapper);
    final PluginSettingsMerger settingsMerger = new PluginSettingsMerger(settings);

    final PluginFinder pluginFinder =
        new PluginFinder(new UiHostSettings(), zipReader, jsonReader, settingsMerger);

    final List<PluginJson> list =
        pluginFinder.readPluginsFromDirectory(file).collect(Collectors.toList());

    assertThat(list).hasSize(3);
    assertThat(list.stream().map(PluginJson::getId)).contains("pluginA", "pluginB", "pluginC");
    assertThat(
            list.stream()
                .filter(p -> p.getId().equalsIgnoreCase("pluginB"))
                .findFirst()
                .get()
                .getSettings()
                .get())
        .containsEntry("key", "value");
  }

  @Test
  public void testWithOveride() throws URISyntaxException {
    final UiHostSettings settings = new UiHostSettings();

    final PluginOverride override = new PluginOverride();
    override.setId("pluginB");
    override.setSettings(new HashMap<>());
    override.getSettings().put("key", "value2");
    settings.setOverride(Arrays.asList(override));

    final File file = readFileFromResources("plugins");

    final ObjectMapper mapper = new ObjectMapper();
    final PluginZipReader zipReader = new PluginZipReader(mapper);
    final PluginJsonReader jsonReader = new PluginJsonReader(mapper);
    final PluginSettingsMerger settingsMerger = new PluginSettingsMerger(settings);

    final PluginFinder pluginFinder =
        new PluginFinder(new UiHostSettings(), zipReader, jsonReader, settingsMerger);

    final List<PluginJson> list =
        pluginFinder.readPluginsFromDirectory(file).collect(Collectors.toList());

    assertThat(
            list.stream()
                .filter(p -> p.getId().equalsIgnoreCase("pluginB"))
                .findFirst()
                .get()
                .getSettings()
                .get())
        .containsEntry("key", "value2");
  }
}
