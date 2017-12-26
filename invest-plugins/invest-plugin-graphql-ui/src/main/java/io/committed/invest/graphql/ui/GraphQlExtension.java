package io.committed.invest.graphql.ui;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.committed.invest.extensions.InvestGraphQlExtension;

@Configuration
@ComponentScan(basePackageClasses = GraphQlExtension.class)
@EnableConfigurationProperties(UiPluginsSettings.class)
public class GraphQlExtension implements InvestGraphQlExtension {


}
