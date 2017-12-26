package io.committed.invest.graphql.ui;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.committed.invest.extensions.VesselGraphQlExtension;

@Configuration
@ComponentScan(basePackageClasses = GraphQlPlugin.class)
@EnableConfigurationProperties(UiPluginsSettings.class)
public class GraphQlPlugin implements VesselGraphQlExtension {


}
