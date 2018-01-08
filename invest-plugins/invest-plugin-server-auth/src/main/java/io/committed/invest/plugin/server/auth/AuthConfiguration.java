package io.committed.invest.plugin.server.auth;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import io.committed.invest.plugin.server.auth.config.NoAuthSecurityConfig;

@Configuration
@ComponentScan(basePackageClasses = NoAuthSecurityConfig.class)
public class AuthConfiguration {

}
