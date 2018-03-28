package io.committed.invest.core.dto.analytic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TermBinTest {

  @Test
  public void testCompareTo() {
    // Ordered by count (desc)
    final TermBin a = new TermBin("a", 10);
    final TermBin b = new TermBin("a", 20);

    assertThat(a.compareTo(b)).isGreaterThan(0);
  }

  @Test
  public void testGetTerm() {
    final TermBin b = new TermBin(null, 10);
    assertNotNull(b.getTerm());
  }
}
