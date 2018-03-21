package io.committed.invest.core.constants;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.HashSet;
import org.junit.Test;

public class DateAxisTest {

  @Test
  public void test() {
    assertThat(DateAxis.DAYS_OF_WEEK).hasSize(7);
    assertThat(DateAxis.HOURS_OF_DAY).hasSize(24);
    assertThat(DateAxis.MINUTES_OF_HOUR).hasSize(60);
    assertThat(DateAxis.MONTHS_OF_YEAR).hasSize(12);


    // No dups
    assertThat(new HashSet<>(DateAxis.DAYS_OF_WEEK)).hasSize(7);
    assertThat(new HashSet<>(DateAxis.HOURS_OF_DAY)).hasSize(24);
    assertThat(new HashSet<>(DateAxis.MINUTES_OF_HOUR)).hasSize(60);
    assertThat(new HashSet<>(DateAxis.MONTHS_OF_YEAR)).hasSize(12);
  }

}
