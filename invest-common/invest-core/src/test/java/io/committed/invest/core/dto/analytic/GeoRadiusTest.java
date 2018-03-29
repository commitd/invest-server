package io.committed.invest.core.dto.analytic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GeoRadiusTest {

  @Test
  public void testValid() {
    assertValid(0, 0, 0, true);
    assertValid(0, 0, 10, true);
    assertValid(0, 0, 1000, true);
    assertValid(5, 23, 0, true);
    assertValid(0, -3, 10, true);
    assertValid(34, 34, 1000, true);

    assertValid(-200, 100, 10, false);
    assertValid(-200, 100, 10, false);
    assertValid(-50, 1000, 10, false);
    assertValid(-200, 100, -10, false);
    assertValid(-200, 100, -10, false);
    assertValid(-50, 1000, -10, false);
    assertValid(-50, 50, -10, false);
  }

  @Test
  public void testConvertToBox() {
    final GeoRadius r = new GeoRadius(10.0, 20.0, 5.0);
    final GeoBox b = r.convertToBox();

    assertTrue(b.isValid());
    assertTrue(20 < b.getE());
    assertTrue(20 > b.getW());
    assertTrue(10 > b.getS());
    assertTrue(10 < b.getN());
  }

  private void assertValid(
      final double lat, final double lon, final double rad, final boolean valid) {
    final GeoRadius l = new GeoRadius(lat, lon, rad);
    assertEquals(valid, l.isValid());
  }
}
