package io.committed.invest.core.dto.analytic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NumberRangeTest {

  @Test
  public void testValid() {
    assertValid(null, 10.0, true);
    assertValid(null, null, true);
    assertValid(9.0, 10.0, true);
    assertValid(9.0, null, true);

    assertValid(11.0, 10.0, false);
  }

  private void assertValid(final Double f, final Double t, final boolean valid) {
    final NumberRange l = new NumberRange(f, t);
    assertEquals(valid, l.isValid());
  }
}
