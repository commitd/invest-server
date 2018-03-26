package io.committed.invest.support.elasticsearch.utils;

import java.util.List;
import java.util.stream.Collectors;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import io.committed.invest.core.constants.TimeInterval;
import io.committed.invest.core.dto.analytic.TimeBin;
import io.committed.invest.core.dto.analytic.Timeline;

/**
 * Helper functions for converting from {@link TimeInterval} to database intervals.
 */
public final class TimeIntervalUtils {

  private TimeIntervalUtils() {
    // Singleton
  }


  /**
   * Convert a time intervale to an ES date histogram (approximately)
   *
   * @param interval the interval
   * @return the date histogram interval
   */
  public static DateHistogramInterval toDateHistogram(final TimeInterval interval) {
    if (interval == null) {
      return DateHistogramInterval.MONTH;
    }

    if (interval.equals(TimeInterval.YEAR)) {
      return DateHistogramInterval.YEAR;
    } else if (interval.equals(TimeInterval.MONTH)) {
      return DateHistogramInterval.MONTH;
    } else if (interval.equals(TimeInterval.DAY)) {
      return DateHistogramInterval.DAY;
    } else if (interval.equals(TimeInterval.HOUR)) {
      return DateHistogramInterval.HOUR;
    } else if (interval.equals(TimeInterval.MINUTE)) {
      return DateHistogramInterval.MINUTE;
    } else if (interval.equals(TimeInterval.SECOND)) {
      return DateHistogramInterval.SECOND;
    } else {
      // Default to something safe
      return DateHistogramInterval.MONTH;
    }

  }

  /**
   * Convert a ES date histogram to a time interval (approximately).
   *
   * @param interval the interval
   * @return the time interval
   */
  public static TimeInterval fromDateHistogram(final DateHistogramInterval interval) {
    if (interval == null) {
      return TimeInterval.MONTH;
    }

    if (interval.equals(DateHistogramInterval.YEAR)) {
      return TimeInterval.YEAR;
    } else if (interval.equals(DateHistogramInterval.QUARTER)) {
      return TimeInterval.MONTH;
    } else if (interval.equals(DateHistogramInterval.MONTH)) {
      return TimeInterval.MONTH;
    } else if (interval.equals(DateHistogramInterval.WEEK)) {
      return TimeInterval.DAY;
    } else if (interval.equals(DateHistogramInterval.DAY)) {
      return TimeInterval.DAY;
    } else if (interval.equals(DateHistogramInterval.HOUR)) {
      return TimeInterval.HOUR;
    } else if (interval.equals(DateHistogramInterval.MINUTE)) {
      return TimeInterval.MINUTE;
    } else if (interval.equals(DateHistogramInterval.SECOND)) {
      return TimeInterval.SECOND;
    } else {
      return TimeInterval.MONTH;
    }


  }

  public static final Timeline create(final Histogram histogram, final TimeInterval interval) {
    final List<TimeBin> list = histogram.getBuckets().stream().map(h -> {
      final DateTime key = (DateTime) h.getKey();
      final Instant jodaInstant = key.toInstant();
      return new TimeBin(java.time.Instant.ofEpochMilli(jodaInstant.getMillis()), h.getDocCount());
    }).collect(Collectors.toList());

    return new Timeline(interval, list);
  }
}
