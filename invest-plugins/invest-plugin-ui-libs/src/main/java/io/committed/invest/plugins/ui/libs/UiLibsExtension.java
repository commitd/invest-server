package io.committed.invest.plugins.ui.libs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.invest.extensions.InvestExtension;

/**
 * Plugin hosting JS, CSS, etc libraries which other plugins may wish to access
 *
 * <p>This is NOT UI plugin as we don't want it displayed or mounted in the same way a UI plugin
 * would be.
 */
@Configuration
@Import(UiLibsConfig.class)
public class UiLibsExtension implements InvestExtension {}
