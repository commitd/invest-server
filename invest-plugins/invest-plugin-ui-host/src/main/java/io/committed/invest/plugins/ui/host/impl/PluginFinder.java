package io.committed.invest.plugins.ui.host.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.committed.invest.plugins.ui.host.UiHostSettings;
import io.committed.invest.plugins.ui.host.data.PluginJson;
import lombok.extern.slf4j.Slf4j;

/**
 * Services which will walk the root directories to find candidates locations of plugins.
 *
 * This will find invest.sjon files in ZIP and as plain files.
 *
 */
@Service
@Slf4j
public class PluginFinder {

  private final PluginZipReader zipReader;
  private final PluginSettingsMerger settingsMerger;
  private final PluginJsonReader jsonReader;
  private final UiHostSettings settings;

  @Autowired
  public PluginFinder(final UiHostSettings settings, final PluginZipReader zipReader, final PluginJsonReader jsonReader,
      final PluginSettingsMerger settingsMerger) {
    this.settings = settings;
    this.zipReader = zipReader;
    this.jsonReader = jsonReader;
    this.settingsMerger = settingsMerger;
  }

  public Stream<PluginJson> readPluginsFromDirectory(final File root) {
    // This will look through every dir and every file to see its its a plugin...
    // we could be more efficient and just filter on stuff which might match
    // but then that's what processPlugin does
    try {
      return Files.walk(root.toPath())
          .map(Path::toFile)
          .filter(File::isFile)
          .map(this::processPlugin)
          .filter(Optional::isPresent)
          .map(Optional::get)
          .map(settingsMerger::merge);
    } catch (final IOException e) {
      log.error("Failed to read directory {}: {}", root.getAbsolutePath(), e.getMessage());
      return Stream.empty();
    }
  }


  private Optional<PluginJson> processPlugin(final File f) {
    final String name = f.getName().toLowerCase();
    if (name.equalsIgnoreCase(settings.getPluginFilename())) {
      return jsonReader.read(f);
    } else if (name.endsWith(".zip")) {
      return zipReader.read(f);
    }

    return Optional.empty();
  }
}
