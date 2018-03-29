package io.committed.invest.test.data;

import lombok.Data;

@Data
public class BrokenSetterExample {

  private String test;

  public void setTest(final String t) {
    // Ignore
  }
}
