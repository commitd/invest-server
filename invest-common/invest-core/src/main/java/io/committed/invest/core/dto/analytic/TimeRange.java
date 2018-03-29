package io.committed.invest.core.dto.analytic;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.committed.invest.core.constants.TimeInterval;
import io.committed.invest.core.constants.TimeIntervalConstants;

/** A time range for query. */
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

  /**
   * This is a suggested interval to perform calculations such as aggregations over.
   *
   * <p>If you have a short time range you might want to aggregate over seconds, if you have decades
   * of time range you should aggregate over year.
   *
   * <p>Obviously there is no hard and fast rule for this, but defining here gives a reasonable set
   * of defaults which won't overload the average visualisation.
   *
   * @return a interval
   */
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

  /**
   * Expand this time range so that is encompasses the argument time range.
   *
   * @param r the r
   */
  public void expand(final TimeRange r) {
    if (start == null || (r.getStart() != null && r.getStart().before(start))) {
      start = r.getStart();
    }

    if (end == null || (r.getEnd() != null && r.getEnd().after(end))) {
      end = r.getEnd();
    }
  }
}
