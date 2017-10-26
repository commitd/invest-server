package io.committed.vessel.core.dto.analytic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeoBox {
  public static final GeoBox GLOBE = new GeoBox(90.0, -180.0, -90.0, 180.0);

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

    if (n != null && s != null) {
      this.n = Math.max(n, s);
      this.s = Math.min(n, s);
    } else {
      this.n = n;
      this.s = s;
    }

    if (e != null && w != null) {
      this.e = Math.max(e, w);
      this.w = Math.min(e, w);
    } else {
      this.e = e;
      this.w = w;
    }
  }

  @JsonIgnore
  public double getSafeN() {
    return getN() == null ? 180.0 : getN();
  }

  @JsonIgnore
  public double getSafeS() {
    return getS() == null ? 180.0 : getS();
  }

  @JsonIgnore
  public double getSafeE() {
    return getE() == null ? 180.0 : getE();
  }

  @JsonIgnore
  public double getSafeW() {
    return getW() == null ? 180.0 : getW();
  }

  public boolean contains(final double lat, final double lon) {
    return s <= lat && lat <= n && w <= lon && lon <= e;
  }

  @JsonIgnore
  public boolean isValid() {
    return n != null && s != null && e != null && w != null && -180 <= e && e <= 180 && -180 <= w
        && w <= 180 && -90 <= n && n <= 90 && -90 <= s && s <= 90;
  }

  public GeoBox intersection(final GeoBox geobox) {
    return new GeoBox(
        minOrSet(n, geobox.getN()),
        minOrSet(e, geobox.getE()),
        maxOrSet(s, geobox.getS()),
        maxOrSet(w, geobox.getW()));
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

}
