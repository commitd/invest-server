package io.committed.invest.core.dto.analytic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Range of real times.
 *
 * Validity is considered if all values are non-null and the ordering is correct, or if its open
 * ended (one for from or to is null). Applications might have tighter notions of validity than
 * that.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumberRange {
  @JsonProperty("from")
  private Double from;

  @JsonProperty("to")
  private Double to;

  public boolean isValid() {
    return (from != null && to != null && from < to) || from == null || to == null;
  }
}
