package io.committed.invest.server.core.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.committed.invest.extensions.InvestUiExtension;
import io.committed.invest.extensions.collections.InvestExtensions;
import io.committed.invest.extensions.registry.InvestUiExtensionRegistry;

/**
 * Configuration bean generating a {@link InvestUiExtensionRegistry} of the UI extensions within the
 * application.
 */
@Configuration
public class ExtensionRegistries {

  @Autowired(required = false)
  private List<InvestUiExtension> uiExtensions;

  @Autowired(required = false)
  private List<InvestExtensions> multipleExtensions;

  @Bean
  public InvestUiExtensionRegistry uiExtensionRegistry() {
    return InvestUiExtensionRegistry.create(uiExtensions, multipleExtensions);
  }
}
