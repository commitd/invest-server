package io.committed.invest.support.elasticsearch;

import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.joda.time.DateTime;
import org.joda.time.Instant;

import io.committed.invest.core.dto.analytic.TimeBin;
import io.committed.invest.core.dto.analytic.Timeline;
import io.committed.invest.core.dto.constants.TimeInterval;

public class TimeIntervalUtils {

  public TimeIntervalUtils() {
    // Singleton
  }


  public static DateHistogramInterval toDateHistogram(final TimeInterval interval) {
    if (interval.equals(TimeInterval.YEAR)) {
      return DateHistogramInterval.YEAR;
    } else if (interval.equals(DateHistogramInterval.MONTH)) {
      return DateHistogramInterval.MONTH;
    } else if (interval.equals(DateHistogramInterval.DAY)) {
      return DateHistogramInterval.DAY;
    } else if (interval.equals(DateHistogramInterval.HOUR)) {
      return DateHistogramInterval.HOUR;
    } else if (interval.equals(DateHistogramInterval.MINUTE)) {
      return DateHistogramInterval.MINUTE;
    } else if (interval.equals(DateHistogramInterval.SECOND)) {
      return DateHistogramInterval.SECOND;
    } else {
      // TODO: We could parse the string value of DateHistogramInterval
      return DateHistogramInterval.HOUR;
    }

  }

  public static TimeInterval fromDateHistogram(final DateHistogramInterval interval) {
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
      // TODO: We could parse the string value of DateHistogramInterval
      return TimeInterval.HOUR;
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
