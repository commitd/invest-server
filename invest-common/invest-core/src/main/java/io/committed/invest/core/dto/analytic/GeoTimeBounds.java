package io.committed.invest.core.dto.analytic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Geo box with time bounds
 *
 * <p>Useful for geo-temporal search queries
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
