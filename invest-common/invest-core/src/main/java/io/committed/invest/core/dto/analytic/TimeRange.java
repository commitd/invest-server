package io.committed.invest.core.dto.analytic;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.committed.invest.core.constants.TimeIntervalConstants;
import io.committed.invest.core.dto.constants.TimeInterval;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TimeRange {

  @JsonProperty("start")
  private Date start;

  @JsonProperty("end")
  private Date end;

  @JsonCreator
  public TimeRange(@JsonProperty("start") final Date start, @JsonProperty("end") final Date end) {
    if (start != null && end != null) {
      if (start.before(end)) {
        this.start = start;
        this.end = end;
      } else {
        this.start = end;
        this.end = start;
      }
    } else {
      this.start = start;
      this.end = end;
    }
  }


  public long getDuration() {
    if (end != null && start != null) {
      return end.getTime() - start.getTime();
    } else {
      // WE could create meaning here (if star == null then from epoch to now) but its confusing.
      return 0;
    }
  }

  public TimeInterval getInterval() {

    final long duration = getDuration();

    if (duration < 240 * TimeIntervalConstants.SECOND) {
      return TimeInterval.SECOND;
    } else if (duration < 240 * TimeIntervalConstants.MINUTE) {
      return TimeInterval.MINUTE;
    } else if (duration < 72 * TimeIntervalConstants.HOUR) {
      return TimeInterval.HOUR;
    } else if (duration < 90 * TimeIntervalConstants.DAY) {
      return TimeInterval.DAY;
    } else if (duration < 120 * TimeIntervalConstants.MONTH) {
      return TimeInterval.MONTH;
    } else {
      return TimeInterval.YEAR;
    }

  }

  @JsonIgnore
  public boolean isValid() {
    return start != null && end != null;
  }


  public void expand(final TimeRange r) {
    if (start == null || (r.getStart() != null && r.getStart().before(start))) {
      start = r.getStart();
    }

    if (end == null || (r.getEnd() != null && r.getEnd().before(end))) {
      end = r.getEnd();
    }
  }
}
