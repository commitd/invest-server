package io.committed.invest.core.dto.analytic;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A geohash grid of counts for heatmap.
 *
 * Precision is the provided geohash length;
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoHeatmap {

  @JsonProperty("precision")
  private int precision;

  @JsonProperty("bins")
  private List<GeoBin> bins;

}
