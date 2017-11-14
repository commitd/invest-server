package io.committed.vessel.graphql.ui;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.committed.vessel.extensions.VesselGraphQlExtension;

@Configuration
@ComponentScan(basePackageClasses = GraphQlPlugin.class)
public class GraphQlPlugin implements VesselGraphQlExtension {


}
