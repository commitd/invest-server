package io.committed.vessel.core.dto.analytic;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
