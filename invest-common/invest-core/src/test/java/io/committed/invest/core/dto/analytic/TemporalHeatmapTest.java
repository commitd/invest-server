package io.committed.invest.core.dto.analytic;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class TemporalHeatmapTest {

  @Test
  public void testCreate() {
    final TemporalHeatmap t = new TemporalHeatmap(new String[] {"a", "b", "c"}, new String[] {"x", "y"});

    assertThat(t.getBins()).hasSize(3);
    assertThat(t.getBins().get(0)).hasSize(2);
    assertThat(t.getBins().get(0).get(1)).isEqualTo(0);

  }


  @Test
  public void testAddIntInt() {
    final TemporalHeatmap t = new TemporalHeatmap(new String[] {"a", "b", "c"}, new String[] {"x", "y"});

    assertThat(t.getBins().get(0).get(1)).isEqualTo(0);

    t.add(0, 1);

    assertThat(t.getBins().get(0).get(0)).isEqualTo(0);
    assertThat(t.getBins().get(0).get(1)).isEqualTo(1);

  }

  @Test
  public void testAddIntIntLong() {
    final TemporalHeatmap t = new TemporalHeatmap(new String[] {"a", "b", "c"}, new String[] {"x", "y"});

    assertThat(t.getBins().get(0).get(1)).isEqualTo(0);

    t.add(0, 1, 10L);

    assertThat(t.getBins().get(0).get(0)).isEqualTo(0);
    assertThat(t.getBins().get(0).get(1)).isEqualTo(10);
  }

}
