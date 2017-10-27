package io.committed.vessel.graphql.ui;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.committed.vessel.extensions.VesselGraphQlExtension;

public class GraphQlPlugin implements VesselGraphQlExtension {

  @Override
  public Class<?> getConfiguration() {
    return PluginConfiguration.class;
  }

  @Configuration
  @ComponentScan(basePackageClasses = GraphQlPlugin.class)
  public static class PluginConfiguration {

  }

}
