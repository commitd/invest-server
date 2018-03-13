package io.committed.invest.core.dto.analytic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.committed.invest.core.utils.GeoUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A radius around a centre point.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoRadius {

  @JsonProperty("lat")
  private Double lat;

  @JsonProperty("lon")
  private Double lon;

  @JsonProperty("radius")
  private Double radius;

  public GeoBox convertToBox() {
    if (!isValid()) {
      return new GeoBox(Double.NaN, Double.NaN, Double.NaN, Double.NaN);
    }

    return GeoUtil.createBoundingBox(getLat(), getLon(), getRadius());
  }

  // Bounding box surrounding the point at given coordinates,
  // assuming local approximation of Earth surface as a sphere
  // of radius given by WGS84

  @JsonIgnore
  public boolean isValid() {
    return Double.isFinite(lat) && Double.isFinite(lon) &&
        -180 <= lon && lon <= 180 &&
        -90 <= lat && lat <= 90 &&
        0 <= radius;
  }
}
