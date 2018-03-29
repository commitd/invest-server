package io.committed.invest.test.data;

import lombok.Data;

@Data
public class BrokenEqualsExample {

  private String test;

  @Override
  public int hashCode() {
    return test.hashCode();
  }

  @Override
  public boolean equals(final Object o) {
    return true;
  }
}
