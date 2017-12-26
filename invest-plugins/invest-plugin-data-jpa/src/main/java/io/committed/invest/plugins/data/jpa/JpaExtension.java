package io.committed.invest.plugins.data.jpa;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.invest.extensions.InvestDataExtension;

@Configuration
@Import(JpaConfiguration.class)
public class JpaExtension implements InvestDataExtension {

}
