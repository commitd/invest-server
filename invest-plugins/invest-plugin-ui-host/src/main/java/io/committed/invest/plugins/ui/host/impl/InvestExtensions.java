package io.committed.invest.plugins.ui.host.impl;

import java.util.Collection;
import java.util.stream.Stream;
import io.committed.invest.extensions.InvestExtension;
import lombok.Data;

@Data
public class InvestExtensions<T extends InvestExtension> {

  private final Collection<T> extensions;

  public boolean isEmpty() {
    return extensions.isEmpty();
  }

  public Stream<T> stream() {
    return extensions.stream();
  }

}
