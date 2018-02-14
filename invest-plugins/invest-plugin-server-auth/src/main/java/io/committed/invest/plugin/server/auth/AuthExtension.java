package io.committed.invest.plugin.server.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import io.committed.invest.extensions.InvestExtension;

@Configuration
@Import(AuthConfiguration.class)
public class AuthExtension implements InvestExtension {


}
