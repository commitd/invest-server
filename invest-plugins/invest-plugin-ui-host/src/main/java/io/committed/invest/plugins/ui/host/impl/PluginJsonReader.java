package io.committed.invest.plugins.ui.host.impl;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.committed.invest.plugins.ui.host.data.PluginJson;
import lombok.extern.slf4j.Slf4j;

/**
 * Service which reads plugin json from the filesystem
 *
 */
@Service
@Slf4j
public class PluginJsonReader {

  private final ObjectMapper mapper;

  @Autowired
  public PluginJsonReader(final ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public Optional<PluginJson> read(final File f) {
    try {
      return processPluginJson(mapper.readValue(f, PluginJson.class), f);
    } catch (final IOException e) {
      log.warn("Unable to process {} as UI plugin", f.getAbsolutePath(), e);
      return Optional.empty();
    }
  }

  public Optional<PluginJson> processPluginJson(final PluginJson json, final File f) {
    // Check a resource to mount provide access
    final PathResource r = new PathResource(f.getAbsoluteFile().getParentFile().toPath());
    json.setResource(r);

    return Optional.of(json);
  }


}
