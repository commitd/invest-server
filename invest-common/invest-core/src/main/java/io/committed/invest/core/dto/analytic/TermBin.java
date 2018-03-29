package io.committed.invest.core.dto.analytic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A term count.
 *
 * <p>Used for aggregations (top term in document, etc).
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
