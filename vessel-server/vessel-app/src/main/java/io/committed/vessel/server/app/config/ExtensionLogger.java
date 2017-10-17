package io.committed.vessel.server.app.config;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.committed.vessel.extensions.VesselExtension;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExtensionLogger {

  @Autowired(required = false)
  private List<VesselExtension> extensions;

  @PostConstruct
  public void postConstruct() {
    if (extensions == null || extensions.isEmpty()) {
      log.warn("No extensions");
    } else {
      extensions.stream().forEach(this::logExtension);
    }
  }

  private void logExtension(final VesselExtension e) {
    log.info("Found extension {} with name {} of type {}", e.getId(), e.getName(), e.getClass());
  }
}
