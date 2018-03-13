package io.committed.invest.core.utils;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

public class FieldUtilsTest {

  @Test
  public void testSplit() {
    assertThat(FieldUtils.fieldSplitter("a.b.c")).containsExactly("a", "b", "c");
    assertThat(FieldUtils.fieldSplitter("a")).containsExactly("a");
    assertThat(FieldUtils.fieldSplitter("a..b.c")).containsExactly("a", "b", "c");
  }

  @Test
  public void testJoinList() {
    assertThat(FieldUtils.joinField(Collections.emptyList())).isEmpty();
    assertThat(FieldUtils.joinField(Arrays.asList("a", "b", "c"))).isEqualTo("a.b.c");

  }

  @Test
  public void testJoinArray() {
    assertThat(FieldUtils.joinField(new String[0])).isEmpty();
    assertThat(FieldUtils.joinField("a", "b", "c")).isEqualTo("a.b.c");
  }
}
