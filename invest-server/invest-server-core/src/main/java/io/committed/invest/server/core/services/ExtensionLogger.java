package io.committed.invest.server.core.services;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.committed.invest.extensions.InvestExtension;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExtensionLogger {

  @Autowired(required = false)
  private List<InvestExtension> extensions;

  @PostConstruct
  public void postConstruct() {
    if (extensions == null || extensions.isEmpty()) {
      log.warn("No extensions");
    } else {
      extensions.stream().forEach(this::logExtension);
    }
  }

  private void logExtension(final InvestExtension e) {
    log.info("Found extension {} with name {} of type {}", e.getId(), e.getName(), e.getClass());
  }
}
