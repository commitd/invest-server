package io.committed.vessel.plugins.gql.extensions;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.committed.vessel.extensions.VesselUiExtension;

@Configuration
public class VesselGraphQlExtensionConfig {

  @Autowired(required = false)
  private final List<VesselUiExtension> extensions = Collections.emptyList();

  @Bean
  public GraphQlUiExtensionService graphQlExtensionService() {
    return new GraphQlUiExtensionService(extensions);
  }

}
