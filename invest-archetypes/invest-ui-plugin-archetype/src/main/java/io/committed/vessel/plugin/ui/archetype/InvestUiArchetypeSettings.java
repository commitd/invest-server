package io.committed.vessel.plugin.ui.archetype;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configurable settings for the VesselUiArchetype plugin.
 * 
 * Use the Spring application.properties or application.yaml to set 
 * properties.
 * 
 * For example:
 * 
 * If you have 
 * 
 * <pre>
 * private int maxResults = 10;
 * </pre>
 * 
 * Then in Yaml you can override this with:
 * 
 * <pre>
 * VesselUiArchetype:
 *  max-results: 100
 * </pre>
 * 
 * You can access all the settings from your UI plugin on the browser with a GraphQL query:
 * 
 * <pre>
 * query {
 *  vesselServer: {
 *   plugin(id:"VesselUiArchetype") {
 *     settings
 *   }
 *  }
 * }
 * </pre>
 * 
 * 
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("VesselUiArchetype")
@Data
public class InvestUiArchetypeSettings {

    // TODO: Create any settings you need here
}