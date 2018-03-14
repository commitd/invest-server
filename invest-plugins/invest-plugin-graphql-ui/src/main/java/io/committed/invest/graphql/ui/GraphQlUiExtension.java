package io.committed.invest.graphql.ui;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import io.committed.invest.extensions.InvestGraphQlExtension;

/**
 * Extension which offers GraphQl resolvers to support the UI.
 */
@Configuration
@ComponentScan(basePackageClasses = GraphQlUiExtension.class)
@EnableConfigurationProperties(UiPluginsSettings.class)
public class GraphQlUiExtension implements InvestGraphQlExtension {


}
