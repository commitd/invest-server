package io.committed.invest.core.utils;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import io.committed.invest.core.dto.analytic.GeoBox;

public class GeoUtilTest {

  @Test
  public void test() {
    final GeoBox boundingBox = GeoUtil.createBoundingBox(10, 20, 1000);

    assertThat(boundingBox.getE()).isGreaterThan(20);
    assertThat(boundingBox.getW()).isLessThan(20);
    assertThat(boundingBox.getS()).isLessThan(10);
    assertThat(boundingBox.getN()).isGreaterThan(10);

  }

  @Test
  public void testEdge() {
    final GeoBox boundingBox = GeoUtil.createBoundingBox(90, 180, 1000);

    assertThat(boundingBox.getE()).isGreaterThan(180);
    assertThat(boundingBox.getW()).isLessThan(180);
    assertThat(boundingBox.getS()).isLessThan(90);
    assertThat(boundingBox.getN()).isGreaterThan(90);

    assertThat(boundingBox.getSafeE()).isEqualTo(180);
    assertThat(boundingBox.getSafeN()).isEqualTo(90);

  }

}
