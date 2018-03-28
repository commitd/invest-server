package io.committed.invest.core.dto.analytic;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A geohash grid of counts for heatmap.
 *
 * <p>Precision is the provided geohash length;
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
