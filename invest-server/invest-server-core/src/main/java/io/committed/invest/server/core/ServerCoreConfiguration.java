package io.committed.invest.server.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import io.committed.invest.server.core.annotations.InvestApplication;

/**
 * A configuration bean which importa all the core configuration needed for an Invest server.
 *
 * Rather than {@link Import} this directly use {@link InvestApplication}, unless you want customise
 * the other annotations there.
 *
 */
@Configuration
@ComponentScan(basePackageClasses = ServerCoreConfiguration.class)
public class ServerCoreConfiguration {

}
