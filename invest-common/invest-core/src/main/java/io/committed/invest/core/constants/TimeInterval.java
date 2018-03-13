package io.committed.invest.core.constants;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

public enum TimeInterval {
  SECOND() {
    @Override
    public int mapTime(final ZonedDateTime time) {
      return time.getSecond();
    }
  },
  MINUTE() {
    @Override
    public int mapTime(final ZonedDateTime time) {
      return time.getMinute();
    }
  },
  HOUR() {
    @Override
    public int mapTime(final ZonedDateTime time) {
      return time.getHour();
    }
  },
  DAY() {
    @Override
    public int mapTime(final ZonedDateTime time) {
      return time.getDayOfWeek().ordinal();
    }
  },
  MONTH() {
    @Override
    public int mapTime(final ZonedDateTime time) {
      return time.getMonth().ordinal();
    }
  },
  YEAR() {
    @Override
    public int mapTime(final ZonedDateTime time) {
      return time.getYear();
    }
  };


  public static List<String> getAxis(final TimeInterval x) {
    switch (x) {
      case MONTH:
        return DateAxis.MONTHS_OF_YEAR;
      case DAY:
        return DateAxis.DAYS_OF_WEEK;
      case HOUR:
        return DateAxis.HOURS_OF_DAY;
      case MINUTE:
        return DateAxis.MINUTES_OF_HOUR;
      default:
        return Collections.emptyList();
    }
  }

  public static TimeInterval getSmaller(final TimeInterval x, final TimeInterval y) {
    if (x.compareTo(y) < 0) {
      return x;
    } else {
      return y;
    }

  }

  public int mapTime(final ZonedDateTime time) {
    // TODO: What is this for!!
    return 0;
  }
}
