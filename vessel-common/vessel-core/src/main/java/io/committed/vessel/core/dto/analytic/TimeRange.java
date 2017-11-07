package io.committed.vessel.core.dto.analytic;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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


  @JsonIgnore
  public long getDuration() {
    if (end != null && start != null) {
      return end.getTime() - start.getTime();
    } else {
      // WE could create meaning here (if star == null then from epoch to now) but its confusing.
      return 0;
    }

  }

  @JsonIgnore
  public boolean isValid() {
    return start != null && end != null;
  }
}
