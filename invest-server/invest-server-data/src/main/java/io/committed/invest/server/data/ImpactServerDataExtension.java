package io.committed.invest.server.data;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import io.committed.invest.extensions.InvestServiceExtension;
import lombok.extern.slf4j.Slf4j;

@Configuration
@ComponentScan(basePackageClasses = ImpactServerDataExtension.class)
@Slf4j
public class ImpactServerDataExtension implements InvestServiceExtension {



}
