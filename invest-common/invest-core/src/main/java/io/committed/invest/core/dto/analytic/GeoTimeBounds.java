package io.committed.invest.core.dto.analytic;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeoTimeBounds {
  @JsonProperty("geo")
  private GeoBox geo;

  @JsonProperty("time")
  private TimeRange time;

}
