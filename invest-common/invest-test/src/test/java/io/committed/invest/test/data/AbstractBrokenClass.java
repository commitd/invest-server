package io.committed.invest.test.data;

import lombok.Data;

@Data
public abstract class AbstractBrokenClass {

  private String test;

  public abstract void doSomething();

  @Override
  public boolean equals(final Object o) {
    return o != null;
  }
}
