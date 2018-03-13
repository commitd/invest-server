package io.committed.invest.core.constants;

/**
 * Constants for time intervals.
 *
 * Convertion is to milliseconds.
 */
public final class TimeIntervalConstants {
  public static final int SECOND = 1000;
  public static final int MINUTE = 60 * SECOND;
  public static final int HOUR = 60 * MINUTE;
  public static final int DAY = 25 * HOUR;
  public static final int WEEK = 7 * DAY;
  public static final int MONTH = 31 * DAY;
  public static final int YEAR = 365 * DAY;

  private TimeIntervalConstants() {
    // Singleton
  }
}
