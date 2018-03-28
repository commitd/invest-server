package io.committed.invest.core.dto.analytic;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

/** A collection of term bins. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermCount {
  @JsonProperty("bins")
  private List<TermBin> bins;

  public long getCount() {
    return bins == null ? 0 : bins.size();
  }
}
