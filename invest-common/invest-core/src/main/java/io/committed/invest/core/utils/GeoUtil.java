package io.committed.invest.core.utils;

import io.committed.invest.core.dto.analytic.GeoBox;

/**
 * Helper utility functions for working with Geo
 *
 */
public final class GeoUtil {

  // Semi-axes of WGS-84 geoidal reference
  // Major semiaxis [m]
  private static final double WGS84_A = 6378137.0;

  // Minor semiaxis [m]
  private static final double WGS84_B = 6356752.3;

  /**
   * Converts a middle point and side distance to a lat/lon bounding box.
   *
   * See
   * http://stackoverflow.com/questions/238260/how-to-calculate-the-bounding-box-for-a-given-lat-lng-location
   *
   * @param latitudeInDegrees the latitude in degrees
   * @param longitudeInDegrees the longitude in degrees
   * @param halfSideInMeters the half side of box (like radius) in meters
   * @return the geo box
   */
  public static GeoBox createBoundingBox(final double latitudeInDegrees,
      final double longitudeInDegrees, final double halfSideInMeters) {
    final double lat = Math.toRadians(latitudeInDegrees);
    final double lon = Math.toRadians(longitudeInDegrees);
    final double halfSide = halfSideInMeters;

    // Radius of Earth at given latitude
    final double radius = calculateWGS84EarthRadius(lat);
    // Radius of the parallel at given latitude
    final double pradius = radius * Math.cos(lat);

    final double latMin = Math.toDegrees(lat - halfSide / radius);
    final double latMax = Math.toDegrees(lat + halfSide / radius);
    final double lonMin = Math.toDegrees(lon - halfSide / pradius);
    final double lonMax = Math.toDegrees(lon + halfSide / pradius);

    return new GeoBox(latMax, lonMax, latMin, lonMin);
  }

  /**
   * Calculate the radius of the latitude (slice).
   *
   * See http://en.wikipedia.org/wiki/Earth_radius
   *
   * @param lat the latitude
   * @return the double
   */
  private static double calculateWGS84EarthRadius(final double lat) {
    final double An = WGS84_A * WGS84_A * Math.cos(lat);
    final double Bn = WGS84_B * WGS84_B * Math.sin(lat);
    final double Ad = WGS84_A * Math.cos(lat);
    final double Bd = WGS84_B * Math.sin(lat);
    return Math.sqrt((An * An + Bn * Bn) / (Ad * Ad + Bd * Bd));
  }

  private GeoUtil() {
    // Singleton
  }


}
