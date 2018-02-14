package io.committed.invest.core.dto.analytic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeoBin {

  @JsonProperty("geohash")
  private String geohash;

  @JsonProperty("count")
  private long count;

}
