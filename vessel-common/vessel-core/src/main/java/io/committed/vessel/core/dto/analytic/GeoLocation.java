package io.committed.vessel.core.dto.analytic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoLocation {

  public static final GeoLocation INVALID = new GeoLocation(Double.NaN, Double.NaN);

  @JsonProperty("lat")
  private double lat;

  @JsonProperty("lon")
  private double lon;

  @JsonProperty("acc")
  private double acc;

  public GeoLocation(final double lat2, final double lon2) {
    lat = lat2;
    lon = lon2;
  }

  @JsonIgnore
  public boolean isValidLocation() {
    return Double.isFinite(lat) && Double.isFinite(lon) && -180 <= lon && lon <= 180 && -90 <= lat
        && lat <= 90;
  }
}
