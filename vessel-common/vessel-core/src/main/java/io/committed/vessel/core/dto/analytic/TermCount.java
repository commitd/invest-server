package io.committed.vessel.core.dto.analytic;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermCount {
  @JsonProperty("terms")
  private Map<String, Long> terms;
}
