package io.committed.invest.plugins.ui.host.impl;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
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

  @Test
  public void test() {
    final UiHostSettings settings = new UiHostSettings();

    final ClassLoader classLoader = getClass().getClassLoader();
    final File file = new File(classLoader.getResource("plugins").getFile());

    final ObjectMapper mapper = new ObjectMapper();
    final PluginZipReader zipReader = new PluginZipReader(mapper);
    final PluginJsonReader jsonReader = new PluginJsonReader(mapper);
    final PluginSettingsMerger settingsMerger = new PluginSettingsMerger(settings);


    final PluginFinder pluginFinder = new PluginFinder(new UiHostSettings(), zipReader, jsonReader, settingsMerger);

    final List<PluginJson> list = pluginFinder.readPluginsFromDirectory(file)
        .collect(Collectors.toList());

    assertThat(list).hasSize(3);
    assertThat(list.stream().map(PluginJson::getId)).contains("pluginA", "pluginB", "pluginC");
    assertThat(list.stream().filter(p -> p.getId().equalsIgnoreCase("pluginB")).findFirst().get().getSettings().get())
        .containsEntry("key", "value");

  }

  @Test
  public void testWithOveride() {
    final UiHostSettings settings = new UiHostSettings();

    final PluginOverride override = new PluginOverride();
    override.setId("pluginB");
    override.setSettings(new HashMap<>());
    override.getSettings().put("key", "value2");
    settings.setOverride(Arrays.asList(override));

    final ClassLoader classLoader = getClass().getClassLoader();
    final File file = new File(classLoader.getResource("plugins").getFile());

    final ObjectMapper mapper = new ObjectMapper();
    final PluginZipReader zipReader = new PluginZipReader(mapper);
    final PluginJsonReader jsonReader = new PluginJsonReader(mapper);
    final PluginSettingsMerger settingsMerger = new PluginSettingsMerger(settings);


    final PluginFinder pluginFinder = new PluginFinder(new UiHostSettings(), zipReader, jsonReader, settingsMerger);

    final List<PluginJson> list = pluginFinder.readPluginsFromDirectory(file)
        .collect(Collectors.toList());

    assertThat(list.stream().filter(p -> p.getId().equalsIgnoreCase("pluginB")).findFirst().get().getSettings().get())
        .containsEntry("key", "value2");

  }

}
