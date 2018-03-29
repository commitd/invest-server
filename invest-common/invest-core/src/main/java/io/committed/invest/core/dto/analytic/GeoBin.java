package io.committed.invest.core.dto.analytic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A bin for geohash counts.
 *
 * <p>Output of, for example, heatmap aggregations.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoBin {

  @JsonProperty("geohash")
  private String geohash;

  @JsonProperty("count")
  private long count;
}
