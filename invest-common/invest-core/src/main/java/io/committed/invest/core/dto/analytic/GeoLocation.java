package io.committed.invest.core.dto.analytic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** A geolocation */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoLocation {

  @JsonProperty("lat")
  private double lat;

  @JsonProperty("lon")
  private double lon;

  @JsonProperty("acc")
  private double acc;

  public GeoLocation(final double lat, final double lon) {
    this.lat = lat;
    this.lon = lon;
  }

  @JsonIgnore
  public boolean isValidLocation() {
    return Double.isFinite(lat)
        && Double.isFinite(lon)
        && -180 <= lon
        && lon <= 180
        && -90 <= lat
        && lat <= 90;
  }
}
