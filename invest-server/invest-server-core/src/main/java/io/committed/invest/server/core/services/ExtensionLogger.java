package io.committed.invest.server.core.services;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.committed.invest.extensions.InvestExtension;
import io.committed.invest.extensions.collections.InvestExtensions;
import lombok.extern.slf4j.Slf4j;

/**
 * On boot log the extensions found.
 *
 */
@Component
@Slf4j
public class ExtensionLogger {

  private final List<InvestExtension> extensions;

  private final List<InvestExtensions> multiExtensions;

  public ExtensionLogger(@Autowired(required = false) final List<InvestExtension> extensions,
      @Autowired(required = false) final List<InvestExtensions> multiExtensions) {
    this.extensions = extensions;
    this.multiExtensions = multiExtensions;

  }

  @PostConstruct
  public void postConstruct() {
    int total = 0;

    if (extensions != null && !extensions.isEmpty()) {
      extensions.forEach(this::logExtension);
      total += extensions.size();
    }

    if (multiExtensions != null && !multiExtensions.isEmpty()) {
      multiExtensions.forEach(m -> m.stream().forEach(this::logExtension));

      total += multiExtensions.stream().reduce(0, (a, e) -> a + e.getExtensions().size(), Integer::sum);
    }

    if (total == 0) {
      log.warn("No extensions found");
    } else {
      log.info("Found {} extensions", total);

    }
  }

  private void logExtension(final InvestExtension e) {
    log.info("Found extension {} with name {} of type {}", e.getId(), e.getName(), e.getClass());
  }
}
