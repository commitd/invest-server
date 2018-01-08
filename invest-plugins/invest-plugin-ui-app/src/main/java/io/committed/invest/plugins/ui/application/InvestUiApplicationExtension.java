package io.committed.invest.plugins.ui.application;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import io.committed.invest.extensions.InvestExtension;

/**
 * Plugin hosting the main application.
 *
 * This is NOT UI plugin as we don't want it displayed or mounted in the same way a UI plugin would
 * be.
 */
@Configuration
@EnableConfigurationProperties(UiApplicationSettings.class)
@Import(ApplicationConfig.class)
public class InvestUiApplicationExtension implements InvestExtension {



}
