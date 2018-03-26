package io.committed.invest.core.constants;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.junit.Test;

public class TimeIntervalTest {

  @Test
  public void testGetAxis() {
    assertThat(TimeInterval.getAxis(TimeInterval.SECOND)).isEqualTo(DateAxis.SECONDS_OF_MINUTE);
    assertThat(TimeInterval.getAxis(TimeInterval.MINUTE)).isEqualTo(DateAxis.MINUTES_OF_HOUR);
    assertThat(TimeInterval.getAxis(TimeInterval.HOUR)).isEqualTo(DateAxis.HOURS_OF_DAY);
    assertThat(TimeInterval.getAxis(TimeInterval.DAY)).isEqualTo(DateAxis.DAYS_OF_WEEK);
    assertThat(TimeInterval.getAxis(TimeInterval.MONTH)).isEqualTo(DateAxis.MONTHS_OF_YEAR);

    assertThat(TimeInterval.getAxis(TimeInterval.YEAR)).isEmpty();

  }

  @Test
  public void testGetSmaller() {
    assertThat(TimeInterval.getSmaller(TimeInterval.DAY, TimeInterval.YEAR)).isEqualTo(TimeInterval.DAY);
    assertThat(TimeInterval.getSmaller(TimeInterval.YEAR, TimeInterval.DAY)).isEqualTo(TimeInterval.DAY);
    assertThat(TimeInterval.getSmaller(TimeInterval.MONTH, TimeInterval.MONTH)).isEqualTo(TimeInterval.MONTH);
    assertThat(TimeInterval.getSmaller(TimeInterval.SECOND, TimeInterval.MINUTE)).isEqualTo(TimeInterval.SECOND);
    assertThat(TimeInterval.getSmaller(TimeInterval.SECOND, TimeInterval.YEAR)).isEqualTo(TimeInterval.SECOND);

  }

  @Test
  public void testMapTime() {
    final ZonedDateTime zdt = ZonedDateTime.of(2018, 05, 15, 10, 13, 45, 1, ZoneOffset.UTC);
    assertThat(TimeInterval.SECOND.mapTime(zdt)).isEqualTo(45);
    assertThat(TimeInterval.MINUTE.mapTime(zdt)).isEqualTo(13);
    assertThat(TimeInterval.HOUR.mapTime(zdt)).isEqualTo(10);
    assertThat(TimeInterval.DAY.mapTime(zdt)).isEqualTo(15);
    assertThat(TimeInterval.MONTH.mapTime(zdt)).isEqualTo(5);
    assertThat(TimeInterval.YEAR.mapTime(zdt)).isEqualTo(2018);

  }

}
