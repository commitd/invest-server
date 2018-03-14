package io.committed.invest.graphql.ui.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import io.committed.invest.core.auth.AuthenticationSettings;

public class ServerAuthenticationGraphQlResolverTest {

  @Test
  public void test() {
    final AuthenticationSettings settings = new AuthenticationSettings(true);
    final ServerAuthenticationGraphQlResolver resolver = new ServerAuthenticationGraphQlResolver(settings);

    assertThat(resolver.authentication(null)).isSameAs(settings);
  }

}
