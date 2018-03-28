package io.committed.invest.core.dto.analytic;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

/** A time bin with count. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeBin implements Comparable<TimeBin> {

  @JsonProperty("time")
  private Instant ts;

  @JsonProperty("count")
  private long count;

  @Override
  public int compareTo(final TimeBin o) {
    // Order high to low
    return getTs().compareTo(o.getTs());
  }
}
