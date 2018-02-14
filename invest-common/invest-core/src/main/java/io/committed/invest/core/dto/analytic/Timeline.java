package io.committed.invest.core.dto.analytic;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.committed.invest.core.dto.constants.TimeInterval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Timeline {

  @JsonProperty("interval")
  private TimeInterval interval;

  @JsonProperty("bins")
  private List<TimeBin> bins;
}
