package io.committed.invest.plugins.ui.host.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import com.fasterxml.jackson.databind.ObjectMapper;
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
  public InvestUiExtensions uiHostedExtensions() throws IOException {
    final List<PluginJson> extensions = new LinkedList<>();

    // @formatter:off

    Files.walk(settings.getRoot().toPath())
      .map(Path::toFile)
      .filter(File::isFile)
      .filter(this::isPlugin)
      .map(this::processPlugin)
      .filter(Optional::isPresent)
      .map(Optional::get)
      .forEach(extensions::add);


    // @formatter:on

    return new InvestUiExtensions(extensions);
  }

  private boolean isPlugin(final File f) {
    final String name = f.getName().toLowerCase();
    return name.equalsIgnoreCase(settings.getPluginFilename()) || f.getName().endsWith(".zip");

  }

  private Optional<PluginJson> processPlugin(final File f) {
    if (f.getName().equalsIgnoreCase(settings.getPluginFilename())) {
      try {
        return processPluginJson(mapper.readValue(f, PluginJson.class), f);
      } catch (final IOException e) {
        e.printStackTrace();
        log.warn("Unable to process {} as UI plugin", f.getAbsolutePath(), e);
      }
    } else {
      // TODO: Support zip
    }


    return Optional.empty();
  }

  private Optional<PluginJson> processPluginJson(final PluginJson json, final File f) {
    // TODO: Validity checks / fix issues

    // Check a resource to mount provide access
    final PathResource r = new PathResource(f.getParentFile().toPath());
    json.setResource(r);

    return Optional.of(json);
  }
}
