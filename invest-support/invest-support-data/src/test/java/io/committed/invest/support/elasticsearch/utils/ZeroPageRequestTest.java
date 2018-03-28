package io.committed.invest.support.elasticsearch.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ZeroPageRequestTest {

  @Test
  public void test() {
    final ZeroPageRequest zpr = new ZeroPageRequest();

    assertThat(zpr.getPageSize()).isEqualTo(0);
    assertThat(zpr.getOffset()).isEqualTo(0);
  }
}
