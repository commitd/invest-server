package io.committed.invest.core.dto.analytic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import org.junit.Test;
import io.committed.invest.core.constants.TimeInterval;

public class TimeRangeTest {

  @Test
  public void testOrdered() {
    final TimeRange r = new TimeRange(new Date(20), new Date(10));

    assertEquals(10l, r.getStart().getTime());
    assertEquals(20l, r.getEnd().getTime());
    assertEquals(10l, r.getDuration());
    assertTrue(r.isValid());

    assertEquals(TimeInterval.SECOND, r.getInterval());
  }

  @Test
  public void testNull() {
    final TimeRange r = new TimeRange(null, new Date(10));

    assertEquals(10l, r.getEnd().getTime());
    assertEquals(0, r.getDuration());
    assertFalse(r.isValid());

  }

  @Test
  public void testExpand() {
    final TimeRange r = new TimeRange(new Date(10), new Date(20));
    final TimeRange e = new TimeRange(new Date(15), new Date(30));

    r.expand(e);
    assertEquals(10l, r.getStart().getTime());
    assertEquals(30l, r.getEnd().getTime());

    final TimeRange e2 = new TimeRange(new Date(5), new Date(100));
    r.expand(e2);
    assertEquals(5l, r.getStart().getTime());
    assertEquals(100l, r.getEnd().getTime());
  }

}
