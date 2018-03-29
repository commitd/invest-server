package io.committed.invest.test.data;

import lombok.Data;

@Data
public class BrokenGetterExample {

  private String test;

  public String getTest() {
    return "";
  }
}
