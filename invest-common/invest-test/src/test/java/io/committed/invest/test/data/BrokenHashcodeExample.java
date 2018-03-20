package io.committed.invest.test.data;

import lombok.Data;

@Data
public class BrokenHashcodeExample {

  private String test;

  @Override
  public int hashCode() {
    return 1;
  }

  @Override
  public boolean equals(final Object o) {
    return o != null
        && o instanceof BrokenHashcodeExample
        && (((BrokenHashcodeExample) o).test == test || test != null && test.equals(((BrokenHashcodeExample) o).test));
  }
}
