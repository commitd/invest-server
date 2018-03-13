package io.committed.invest.core.dto.analytic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A bin for geohash counts.
 *
 * Output of, for example, heatmap aggregations.
 */
@Data
@AllArgsConstructor
public class GeoBin {

  @JsonProperty("geohash")
  private String geohash;

  @JsonProperty("count")
  private long count;

}
