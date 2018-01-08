package io.committed.invest.core.dto.analytic;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class TemporalHeatmap {

  @JsonProperty("x")
  private String[] xAxis;

  @JsonProperty("y")
  private String[] yAxis;

  // TODO: Might be neater to make this general 2d heatmap, provide axis info and use a Map rather
  // than List<List> (though axis info would resolve that)
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
