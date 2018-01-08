package io.committed.invest.core.dto.analytic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermBin {
  @JsonProperty("term")
  private String term;

  @JsonProperty("count")
  private long count;

  public String getTerm() {
    return term == null ? "" : term;
  }
}
