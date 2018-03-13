package io.committed.invest.core.dto.analytic;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.committed.invest.core.constants.TimeInterval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A timeline of data.
 *
 * Interval is the interval of data points (ie if its day then each data point in the time bin will
 * be a day).
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Timeline {

  @JsonProperty("interval")
  private TimeInterval interval;

  @JsonProperty("bins")
  private List<TimeBin> bins;
}
