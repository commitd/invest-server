package io.committed.invest.server.data;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import io.committed.invest.extensions.InvestServiceExtension;

@Configuration
@ComponentScan(basePackageClasses = InvestServerDataExtension.class)
public class InvestServerDataExtension implements InvestServiceExtension {

  @Override
  public String getName() {
    return "Invest data services";
  }

  @Override
  public String getDescription() {
    return "Configures and manages data provision accross the application";
  }


}
