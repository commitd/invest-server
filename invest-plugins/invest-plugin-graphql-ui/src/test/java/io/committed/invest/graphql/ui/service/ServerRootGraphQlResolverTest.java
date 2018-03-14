package io.committed.invest.graphql.ui.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class ServerRootGraphQlResolverTest {

  @Test
  public void testNonNull() {
    final ServerRootGraphQlResolver resolver = new ServerRootGraphQlResolver();

    assertThat(resolver.investUiQuery()).isNotNull();
    assertThat(resolver.server()).isNotNull();

  }

}
