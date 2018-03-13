package io.committed.invest.core.dto.analytic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * A geospatial bounding box.
 *
 * Defined by n,s,e,w values lat/lon.
 *
 * Use the getSafe* methods to ensure that values are in range.
 *
 */
@Data
@NoArgsConstructor
public class GeoBox {


  @JsonProperty("n")
  private Double n;

  @JsonProperty("e")
  private Double e;

  @JsonProperty("s")
  private Double s;

  @JsonProperty("w")
  private Double w;

  @JsonCreator
  public GeoBox(@JsonProperty("n") final Double n, @JsonProperty("e") final Double e,
      @JsonProperty("s") final Double s, @JsonProperty("w") final Double w) {
    // NOTE: in doing this ordering we prevent going over international data line etc

    if (n != null && s != null && Double.isFinite(s) && Double.isFinite(n)) {
      this.n = Math.max(n, s);
      this.s = Math.min(n, s);
    } else {
      this.n = n;
      this.s = s;
    }

    if (e != null && w != null && Double.isFinite(e) && Double.isFinite(w)) {
      this.e = Math.max(e, w);
      this.w = Math.min(e, w);
    } else {
      this.e = e;
      this.w = w;
    }
  }

  /**
   * Gets a valid value on N, limited to 90 degrees latitude.
   *
   * @return northern top of the box
   */
  @JsonIgnore
  public double getSafeN() {
    return getN() == null || getN() > 90 || !Double.isFinite(getN()) ? 90.0 : getN();
  }

  /**
   * Gets a valid value on S, limited to -90 degrees latitude.
   *
   * @return southern/bottom of the box
   */
  @JsonIgnore
  public double getSafeS() {
    return getS() == null || getS() < -90 || !Double.isFinite(getS()) ? -90.0 : getS();
  }

  /**
   * Gets a valid value on ES, limited to 180 degrees longitude.
   *
   * @return east/right of the box
   */
  @JsonIgnore
  public double getSafeE() {
    return getE() == null || getE() > 180 || !Double.isFinite(getE()) ? 180.0 : getE();
  }

  /**
   * Gets a valid value on W, limited to -180 degrees longitude.
   *
   * @return west/left of the box
   */
  @JsonIgnore
  public double getSafeW() {
    return getW() == null || getW() < -180 || !Double.isFinite(getW()) ? -180.0 : getW();
  }

  /**
   * Does the box contain the point (inclusive)
   *
   * @param lat the lat
   * @param lon the lon
   * @return true, if successful
   */
  public boolean contains(final double lat, final double lon) {
    return getSafeS() <= lat && lat <= getSafeN() && getSafeW() <= lon && lon <= getSafeE();
  }

  /**
   * Checks if is valid.
   *
   * This means that the raw (constructor provided) values are valid. Note that the output defined by
   * getSafe* will always be safe/valid for use.
   *
   * @return true, if is valid
   */
  @JsonIgnore
  public boolean isValid() {
    return n != null && s != null && e != null && w != null &&
        Double.isFinite(n) && Double.isFinite(s) && Double.isFinite(e) && Double.isFinite(w) &&
        -180 <= e && e <= 180 &&
        -180 <= w && w <= 180 &&
        -90 <= n && n <= 90
        && -90 <= s && s <= 90;
  }


  /**
   * Calculate the intersection this box with another.
   *
   * @param geobox the geobox
   * @return the geo box
   */
  public GeoBox intersection(final GeoBox geobox) {
    return new GeoBox(
        minOrSet(getSafeN(), geobox.getSafeN()), minOrSet(getSafeE(), geobox.getSafeE()),
        maxOrSet(getSafeS(), geobox.getSafeS()), maxOrSet(getSafeW(), geobox.getSafeW()));
  }

  private static Double minOrSet(final Double d1, final Double d2) {
    if (d1 == null) {
      return d2;
    }
    if (d2 == null) {
      return d1;
    }
    return Math.min(d1, d2);
  }

  private static Double maxOrSet(final Double d1, final Double d2) {
    if (d1 == null) {
      return d2;
    }
    if (d2 == null) {
      return d1;
    }
    return Math.max(d1, d2);
  }

  /**
   * The entire globe.
   *
   * Use for search everywhere.
   */
  public static final GeoBox globe() {
    return new GeoBox(90.0, -180.0, -90.0, 180.0);
  }
}
