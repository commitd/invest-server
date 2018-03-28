package io.committed.invest.server.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/** Configuration for WebFlux */
@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {}
