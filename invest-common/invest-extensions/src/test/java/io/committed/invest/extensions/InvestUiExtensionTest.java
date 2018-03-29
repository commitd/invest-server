package io.committed.invest.extensions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class InvestUiExtensionTest {

  @Test
  public void testDefaults() {
    final InvestUiExtension e = new StubInvestUiExtension();

    assertThat(e.getIcon()).isNotBlank();
    assertThat(e.getRoles()).isNotNull();
    assertThat(e.getSettings()).isNotNull();
    assertThat(e.getActions()).isNotNull();
  }

  public static class StubInvestUiExtension implements InvestUiExtension {}
}
