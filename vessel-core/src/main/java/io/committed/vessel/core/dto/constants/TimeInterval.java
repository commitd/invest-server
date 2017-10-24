package io.committed.vessel.core.dto.constants;

import java.time.ZonedDateTime;

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


  public static String[] getAxis(final TimeInterval x) {
    switch (x) {
      default:
        return null;
      case MONTH:
        return DateAxis.MONTHS_OF_YEAR;
      case DAY:
        return DateAxis.DAYS_OF_WEEK;
      case HOUR:
        return DateAxis.HOURS_OF_DAY;
      case MINUTE:
        return DateAxis.MINUTES_OF_HOUR;
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
    return 0;
  }
}
