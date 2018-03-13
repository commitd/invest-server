package io.committed.invest.core.dto.analytic;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * Output of a time histogram/heatmap
 *
 * Useful for pattern of life - where one access might be hour and the other day of week.
 *
 */
@Value
public class TemporalHeatmap {

  @JsonProperty("x")
  private String[] xAxis;

  @JsonProperty("y")
  private String[] yAxis;

  private final List<List<Long>> bins = new ArrayList<>();

  public TemporalHeatmap(final String[] xAxis, final String[] yAxis) {
    this.xAxis = xAxis;
    this.yAxis = yAxis;

    for (final String i : xAxis) {
      final List<Long> day = new ArrayList<>();
      for (final String j : yAxis) {
        day.add(0L);
      }
      bins.add(day);
    }
  }

  public void add(final int x, final int y) {
    add(x, y, 1);
  }

  public void add(final int x, final int y, final long value) {
    final List<Long> b = bins.get(x);
    b.set(y, b.get(y) + value);
  }
}
