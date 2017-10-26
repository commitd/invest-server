package io.committed.vessel.core.dto.analytic;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeBin {

  @JsonProperty("time")
  private long time;

  @JsonProperty("count")
  private long count;

}
