package io.committed.invest.plugin.server.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({ "auth-none" })
@Configuration
public class NoAuthSecurityConfig {

}
