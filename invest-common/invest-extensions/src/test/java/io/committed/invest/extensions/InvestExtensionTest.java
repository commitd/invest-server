package io.committed.invest.extensions;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class InvestExtensionTest {

  @Test
  public void testDefaults() {
    final InvestExtension e = new StubInvestExtension();

    assertThat(e.getId()).isNotBlank();
    assertThat(e.getName()).isNotBlank();
    assertThat(e.getDescription()).isNotNull();
  }


  public static class StubInvestExtension implements InvestExtension {

  }
}
