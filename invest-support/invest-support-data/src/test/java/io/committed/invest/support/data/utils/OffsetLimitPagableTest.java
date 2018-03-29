package io.committed.invest.support.data.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class OffsetLimitPagableTest {

  @Test
  public void testStartPage() {
    final OffsetLimitPagable p = new OffsetLimitPagable(0, 100);

    assertThat(p.getOffset()).isEqualTo(0);
    assertThat(p.getPageSize()).isEqualTo(100);
    assertThat(p.getPageNumber()).isEqualTo(0);

    assertThat(p.first()).isEqualTo(p);
    assertThat(p.previousOrFirst()).isEqualTo(p);

    assertThat(p.next().getOffset()).isEqualTo(100);
    assertThat(p.next().getPageSize()).isEqualTo(100);

    assertThat(p.next().next().getOffset()).isEqualTo(200);
    assertThat(p.next().next().getPageSize()).isEqualTo(100);

    assertThat(p.hasPrevious()).isFalse();

    assertThat(p.getSort()).isEqualTo(p.next().getSort());
    assertThat(p.hashCode()).isNotEqualTo(p.next().hashCode());
  }

  @Test
  public void testOFfsetInPage() {
    final OffsetLimitPagable p = new OffsetLimitPagable(50, 100);

    assertThat(p.getOffset()).isEqualTo(50);
    assertThat(p.getPageSize()).isEqualTo(100);
    assertThat(p.getPageNumber()).isEqualTo(0);

    assertThat(p.first().getOffset()).isEqualTo(0);
    assertThat(p.previousOrFirst().getOffset()).isEqualTo(0);

    assertThat(p.next().getOffset()).isEqualTo(150);
    assertThat(p.next().getPageSize()).isEqualTo(100);

    assertThat(p.next().next().getOffset()).isEqualTo(250);
    assertThat(p.next().next().getPageSize()).isEqualTo(100);

    assertThat(p.hasPrevious()).isTrue();
  }

  @Test
  public void testMiddlePage() {
    final OffsetLimitPagable p = new OffsetLimitPagable(500, 100);

    assertThat(p.getOffset()).isEqualTo(500);
    assertThat(p.getPageSize()).isEqualTo(100);
    assertThat(p.getPageNumber()).isEqualTo(5);

    assertThat(p.hasPrevious()).isTrue();

    assertThat(p.first().getOffset()).isEqualTo(0);
    assertThat(p.previousOrFirst().getOffset()).isEqualTo(400);

    assertThat(p.next().getOffset()).isEqualTo(600);
    assertThat(p.next().getPageSize()).isEqualTo(100);

    assertThat(p.next().next().getOffset()).isEqualTo(700);
    assertThat(p.next().next().getPageSize()).isEqualTo(100);
  }
}
