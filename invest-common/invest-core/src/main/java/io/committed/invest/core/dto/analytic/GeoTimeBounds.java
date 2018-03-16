package io.committed.invest.core.dto.analytic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Geo box with time bounds
 *
 * Useful for geo-temporal search queries
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoTimeBounds {
  @JsonProperty("geo")
  private GeoBox geo;

  @JsonProperty("time")
  private TimeRange time;

}
