package io.committed.invest.core.dto.analytic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GeoBoxTest {

  @Test
  public void testReordering() {
    final GeoBox geoBox = new GeoBox(-45.0, -60.0, 50.0, 70.0);

    assertEquals(70.0, geoBox.getE(), 0.01);
    assertEquals(-60.0, geoBox.getW(), 0.01);
    assertEquals(-45.0, geoBox.getS(), 0.01);
    assertEquals(50.0, geoBox.getN(), 0.01);

    assertTrue(geoBox.isValid());
  }

  @Test
  public void testNaN() {
    final GeoBox geoBox = new GeoBox(-45.0, Double.NaN, 50.0, 70.0);

    assertEquals(70.0, geoBox.getW(), 0.01);
    assertEquals(-45.0, geoBox.getS(), 0.01);
    assertEquals(50.0, geoBox.getN(), 0.01);
    assertTrue(geoBox.getE().isNaN());
    assertEquals(180.0, geoBox.getSafeE(), 0.01);

    assertFalse(geoBox.isValid());
  }

  @Test
  public void testNull() {
    final GeoBox geoBox = new GeoBox(null, null, null, null);

    assertNull(geoBox.getE());
    assertNull(geoBox.getW());
    assertNull(geoBox.getS());
    assertNull(geoBox.getN());

    assertEquals(180.0, geoBox.getSafeE(), 0.01);
    assertEquals(-180.0, geoBox.getSafeW(), 0.01);
    assertEquals(-90.0, geoBox.getSafeS(), 0.01);
    assertEquals(90.0, geoBox.getSafeN(), 0.01);

    assertFalse(geoBox.isValid());
  }

  @Test
  public void testTooLarge() {
    final GeoBox geoBox = new GeoBox(2000.0, 4000.0, -1000.0, -200.0);

    assertEquals(4000, geoBox.getE(), 0.01);
    assertEquals(-200, geoBox.getW(), 0.01);
    assertEquals(-1000, geoBox.getS(), 0.01);
    assertEquals(2000, geoBox.getN(), 0.01);

    assertEquals(180.0, geoBox.getSafeE(), 0.01);
    assertEquals(-180.0, geoBox.getSafeW(), 0.01);
    assertEquals(-90.0, geoBox.getSafeS(), 0.01);
    assertEquals(90.0, geoBox.getSafeN(), 0.01);

    assertFalse(geoBox.isValid());
  }

  @Test
  public void testContains() {
    final GeoBox geoBox = new GeoBox(100.0, 150.0, -10.0, -20.0);

    assertTrue(geoBox.contains(0, 0));
    assertTrue(geoBox.contains(10, 10));
    assertTrue(geoBox.contains(20, 0));
    assertTrue(geoBox.contains(-5, -16));

    assertFalse(geoBox.contains(150, 175));
    assertFalse(geoBox.contains(0, 175));
    assertFalse(geoBox.contains(-2000, 0));
    assertFalse(geoBox.contains(-50, -16));
  }

  @Test
  public void testIntersecton() {
    final GeoBox a = new GeoBox(100.0, 150.0, -10.0, -20.0);
    final GeoBox b = new GeoBox(8.0, 15.0, -80.0, -75.0);
    final GeoBox i = a.intersection(b);

    assertEquals(15, i.getSafeE(), 0.01);
    assertEquals(-20, i.getSafeW(), 0.01);
    assertEquals(-10, i.getSafeS(), 0.01);
    assertEquals(8, i.getSafeN(), 0.01);

    assertTrue(i.isValid());
  }
}
