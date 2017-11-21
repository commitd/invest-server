package io.committed.vessel.plugins.ui.application;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.vessel.extensions.VesselExtension;

/**
 * Plugin hosting the main application.
 *
 * This is NOT UI plugin as we don't want it displayed or mounted in the same way a UI plugin would
 * be.
 */
@Configuration
@EnableConfigurationProperties(VesselUiApplicationSettings.class)
@Import(VesselApplicationConfig.class)
public class VesselUiApplication implements VesselExtension {



}
