package io.committed.invest.core.dto.analytic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A term count.
 *
 * Used for aggregations (top term in document, etc).
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermBin implements Comparable<TermBin> {
  @JsonProperty("term")
  private String term;

  @JsonProperty("count")
  private long count;

  public String getTerm() {
    return term == null ? "" : term;
  }

  @Override
  public int compareTo(final TermBin o) {
    // Order high to low
    return Long.compare(o.getCount(), count);
  }
}
