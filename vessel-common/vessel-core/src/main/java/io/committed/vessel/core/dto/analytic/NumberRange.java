package io.committed.vessel.core.dto.analytic;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumberRange {
  @JsonProperty("from")
  private Double from;

  @JsonProperty("to")
  private Double to;

  public boolean isValid() {
    return from != null && to != null && from < to || to != null || from != null;
  }
}
