package io.committed.invest.core.dto.analytic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GeoLocationTest {

  @Test
  public void testValid() {

    assertValid(-1, -2, true);
    assertValid(90, 180, true);
    assertValid(-90, 180, true);
    assertValid(-90, -180, true);
    assertValid(90, -180, true);
    assertValid(45, 80, true);

    assertValid(1000, -200, false);
    assertValid(1000, 10, false);
    assertValid(1000, 200, false);
    assertValid(-1000, -200, false);
    assertValid(-1000, 20, false);
    assertValid(-1000, 200, false);

    assertValid(190, 190, false);
    assertValid(19, 190, false);
    assertValid(-190, 190, false);
    assertValid(190, -190, false);
    assertValid(19, -190, false);
    assertValid(-190, -100190, false);
  }

  private void assertValid(final double lat, final double lon, final boolean valid) {
    final GeoLocation l = new GeoLocation(lat, lon);
    assertEquals(valid, l.isValidLocation());
  }
}
