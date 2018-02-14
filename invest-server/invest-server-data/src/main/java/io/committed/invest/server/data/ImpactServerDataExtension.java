package io.committed.invest.server.data;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import io.committed.invest.extensions.InvestServiceExtension;

@Configuration
@ComponentScan(basePackageClasses = ImpactServerDataExtension.class)
public class ImpactServerDataExtension implements InvestServiceExtension {



}
