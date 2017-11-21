package io.committed.vessel.plugins.ui.libs;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.vessel.extensions.VesselExtension;

/**
 * Plugin hosting JS, CSS, etc libraries which other plugins may wish to access
 *
 * This is NOT UI plugin as we don't want it displayed or mounted in the same way a UI plugin would
 * be.
 */
@Configuration
@Import(VesselUiLibsConfig.class)
public class VesselUiLibs implements VesselExtension {



}
