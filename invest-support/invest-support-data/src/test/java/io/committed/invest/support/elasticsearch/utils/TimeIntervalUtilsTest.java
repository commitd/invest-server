package io.committed.invest.support.elasticsearch.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram.Bucket;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Mockito;

import io.committed.invest.core.constants.TimeInterval;
import io.committed.invest.core.dto.analytic.Timeline;

public class TimeIntervalUtilsTest {

  @Test
  public void testTo() {
    final Map<TimeInterval, DateHistogramInterval> to = new HashMap<>();

    to.put(TimeInterval.YEAR, DateHistogramInterval.YEAR);
    to.put(TimeInterval.MONTH, DateHistogramInterval.MONTH);
    to.put(TimeInterval.DAY, DateHistogramInterval.DAY);
    to.put(TimeInterval.HOUR, DateHistogramInterval.HOUR);
    to.put(TimeInterval.MINUTE, DateHistogramInterval.MINUTE);
    to.put(TimeInterval.SECOND, DateHistogramInterval.SECOND);

    to.forEach(
        (ti, dh) -> {
          assertThat(TimeIntervalUtils.toDateHistogram(ti)).isEqualTo(dh);
        });
  }

  @Test
  public void testFrom() {
    final Map<DateHistogramInterval, TimeInterval> from = new HashMap<>();

    from.put(DateHistogramInterval.YEAR, TimeInterval.YEAR);
    from.put(DateHistogramInterval.MONTH, TimeInterval.MONTH);
    from.put(DateHistogramInterval.DAY, TimeInterval.DAY);
    from.put(DateHistogramInterval.HOUR, TimeInterval.HOUR);
    from.put(DateHistogramInterval.MINUTE, TimeInterval.MINUTE);
    from.put(DateHistogramInterval.SECOND, TimeInterval.SECOND);

    from.forEach(
        (dh, ti) -> {
          assertThat(TimeIntervalUtils.fromDateHistogram(dh)).isEqualTo(ti);
        });
  }

  @Test
  public void testFromNull() {
    assertThat(TimeIntervalUtils.fromDateHistogram(null)).isEqualTo(TimeInterval.MONTH);
  }

  @Test
  public void testToNull() {
    assertThat(TimeIntervalUtils.toDateHistogram(null)).isEqualTo(DateHistogramInterval.MONTH);
  }

  @Test
  public void testEmptyCreate() {
    final Histogram histogram = Mockito.mock(Histogram.class);

    when(histogram.getBuckets()).thenReturn(Collections.emptyList());
    final Timeline timeline = TimeIntervalUtils.create(histogram, TimeInterval.HOUR);
    assertThat(timeline.getInterval()).isEqualTo(TimeInterval.HOUR);
    assertThat(timeline.getBins()).isEmpty();
  }

  @Test
  public void testCreate() {
    final ParsedDateHistogram histogram = mock(ParsedDateHistogram.class);

    final ParsedDateHistogram.ParsedBucket b1 = mock(ParsedDateHistogram.ParsedBucket.class);
    when(b1.getKey()).thenReturn(new DateTime(0));
    when(b1.getDocCount()).thenReturn(100L);

    final ParsedDateHistogram.ParsedBucket b2 = mock(ParsedDateHistogram.ParsedBucket.class);
    when(b2.getKey()).thenReturn(new DateTime(10));
    when(b2.getDocCount()).thenReturn(50L);

    final List<? extends Bucket> list = Arrays.asList(b1, b2);
    Mockito.doReturn(list).when(histogram).getBuckets();

    final Timeline timeline = TimeIntervalUtils.create(histogram, TimeInterval.HOUR);
    assertThat(timeline.getInterval()).isEqualTo(TimeInterval.HOUR);
    assertThat(timeline.getBins()).hasSize(2);
    assertThat(timeline.getBins().get(0).getTs().toEpochMilli()).isEqualTo(0);
    assertThat(timeline.getBins().get(0).getCount()).isEqualTo(100);
    assertThat(timeline.getBins().get(1).getTs().toEpochMilli()).isEqualTo(10);
    assertThat(timeline.getBins().get(1).getCount()).isEqualTo(50);
  }
}
