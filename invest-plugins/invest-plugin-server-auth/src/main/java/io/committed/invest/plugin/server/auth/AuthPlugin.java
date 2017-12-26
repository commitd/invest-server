package io.committed.invest.plugin.server.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.invest.extensions.VesselExtension;

@Configuration
@Import(AuthConfiguration.class)
public class AuthPlugin implements VesselExtension {


}
