package io.committed.invest.plugins.ui.host.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.committed.invest.plugins.ui.host.data.PluginJson;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PluginZipReader {

  private final ObjectMapper mapper;

  @Autowired
  public PluginZipReader(final ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public Optional<PluginJson> read(final File f) {
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
}
