package io.committed.invest.core.dto.analytic;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.Test;

public class TimeBinTest {

  @Test
  public void testCompareTo() {
    // Ordered by instant
    final TimeBin a = new TimeBin(Instant.ofEpochMilli(10), 1);
    final TimeBin b = new TimeBin(Instant.ofEpochMilli(20), 1);

    assertThat(a.compareTo(b)).isLessThan(0);
  }
}
