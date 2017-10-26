package io.committed.vessel.plugin.server.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({ "auth-none", "default" })
@Configuration
public class NoAuthSecurityConfig {

}
