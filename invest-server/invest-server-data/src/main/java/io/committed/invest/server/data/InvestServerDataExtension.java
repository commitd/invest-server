package io.committed.invest.server.data;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.committed.invest.extensions.InvestServiceExtension;
import io.committed.invest.extensions.data.dataset.Dataset;
import io.committed.invest.extensions.data.providers.DataProviderFactory;
import io.committed.invest.extensions.data.providers.DataProviders;

/**
 * An extensions provides configuration data providers and factories to the rest of the
 * applicaction.
 *
 * <p>Other plugins should declare {@link DataProviderFactory} beans, and the configuration (Yaml or
 * through Beans) should create a list of {@link Dataset}s. These will then be hooked together by
 * this extension.
 *
 * <p>Those wishing to access the data should inject {@link DataProviders} from which they can
 * access the DataProviders they require.
 */
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
