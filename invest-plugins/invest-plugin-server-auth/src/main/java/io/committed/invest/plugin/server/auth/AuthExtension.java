package io.committed.invest.plugin.server.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.invest.extensions.InvestExtension;

/**
 * An extension which provides authentication support for Invest.
 *
 * <p>This does not lock down GraphQL or any other end point. That is the responsibility of the
 * implementor of the GraphQL resolves (etc).
 *
 * <p>However it provides the mechanism by which sessions and authentication is managed and provided
 * by Invest, and abstracts different authentication implementations.
 *
 * <p>Authentication implementations are managed by profiles.
 */
@Configuration
@Import(AuthConfiguration.class)
public class AuthExtension implements InvestExtension {

  @Override
  public String getName() {
    return "Invest Authentication";
  }

  @Override
  public String getDescription() {
    return "Authentication and authorisation for Invest";
  }
}
