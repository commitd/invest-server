package io.committed.invest.plugins.ui.host.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.UrlResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.committed.invest.extensions.collections.InvestExtensions;
import io.committed.invest.plugins.ui.host.UiHostSettings;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class UiFinder {


  private final UiHostSettings settings;
  private final ObjectMapper mapper;

  public UiFinder(final UiHostSettings settings, final ObjectMapper mapper) {
    this.settings = settings;
    this.mapper = mapper;
  }

  @Bean
  public InvestHostedUiExtensions pluginJsonUiHostedExtensions() throws IOException {
    return findUiHostedExtensions();
  }

  @Bean
  public InvestExtensions uiHostedExtensions() throws IOException {
    return new InvestExtensions(pluginJsonUiHostedExtensions().getExtensions());
  }

  private InvestHostedUiExtensions findUiHostedExtensions() throws IOException {
    final List<PluginJson> extensions = settings.getRoots().stream()
        .flatMap(this::readPluginsFromDirectory)
        .collect(Collectors.toList());

    return new InvestHostedUiExtensions(extensions);
  }

  private Stream<PluginJson> readPluginsFromDirectory(final File root) {
    // TODO: This will look through every dir and every file to see its its a plugin...
    // we could be more efficient and just filter on stuff which might match
    // but then that's what processPlugin does
    try {
      return Files.walk(root.toPath())
          .map(Path::toFile)
          .filter(File::isFile)
          .map(this::processPlugin)
          .filter(Optional::isPresent)
          .map(Optional::get);
    } catch (final IOException e) {
      log.error("Failed to read directory {}", root.getAbsolutePath(), e);
      return Stream.empty();
    }
  }


  private Optional<PluginJson> processPlugin(final File f) {
    final String name = f.getName().toLowerCase();
    if (name.equalsIgnoreCase(settings.getPluginFilename())) {
      try {
        return processPluginJson(mapper.readValue(f, PluginJson.class), f);
      } catch (final IOException e) {
        log.warn("Unable to process {} as UI plugin", f.getAbsolutePath(), e);
      }
    } else if (name.endsWith(".zip")) {
      // TODO: Deal with zip..
      // Open up the zip file, look for a invest.json
      // if has it then read that, and see if the FilesystemResource will work.
      return processZip(f);
    }

    return Optional.empty();
  }

  private Optional<PluginJson> processZip(final File f) {
    try (ZipFile zipFile = new ZipFile(f)) {
      final ZipEntry entry = zipFile.getEntry("invest.json");
      if (entry != null) {

        try (InputStream is = zipFile.getInputStream(entry)) {
          // We can serve the resources straight from the zip... nice!
          final URI uri = URI.create("jar:" + f.toURI() + "!/");
          final PluginJson json = mapper.readValue(is, PluginJson.class);
          json.setResource(new UrlResource(uri));
          return Optional.of(json);
        }
      }
    } catch (final IOException e) {
      log.warn("Unable to open {} as a zip file", f.getAbsolutePath());
    }

    return Optional.empty();
  }

  private Optional<PluginJson> processPluginJson(final PluginJson json, final File f) {
    // TODO: Validity checks / fix issues

    // Check a resource to mount provide access
    final PathResource r = new PathResource(f.getAbsoluteFile().getParentFile().toPath());
    json.setResource(r);


    mergeSettings(json);

    return Optional.of(json);
  }

  private void mergeSettings(final PluginJson json) {
    final Optional<Map<String, Object>> overriddenSettings = settings.getSettingsForPluginId(json.getId());

    if (!overriddenSettings.isPresent()) {
      return;
    }

    if (!json.getSettings().isPresent()) {
      json.setSettings(overriddenSettings.get());
      return;
    }

    // We need to merge the settings.. we go invest.json then settings
    // NOTE: this isn't a deep merge (that seems dangerous)
    final Map<String, Object> merged = new HashMap<>();
    merged.putAll(json.getSettings().get());
    merged.putAll(overriddenSettings.get());
    json.setSettings(merged);
  }
}
