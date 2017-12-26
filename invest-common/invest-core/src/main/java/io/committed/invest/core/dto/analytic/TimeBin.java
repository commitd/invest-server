package io.committed.invest.core.dto.analytic;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeBin {

  @JsonProperty("time")
  private Instant ts;

  @JsonProperty("count")
  private long count;

}
