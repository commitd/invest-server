package io.committed.vessel.plugin.server.auth;

import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public abstract class AbstractWithAuthSecurityConfig {

}
