package io.committed.invest.extensions.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;
import io.committed.invest.extensions.InvestExtension;

public abstract class AbstractInvestRegistry<T extends InvestExtension> {

  private final Collection<T> extensions;

  public AbstractInvestRegistry(final Collection<T> extensions) {
    this.extensions = Collections.unmodifiableCollection(extensions);
  }

  public Collection<T> getExtensions() {
    return extensions;
  }

  public Stream<T> stream() {
    return extensions.stream();
  }

  public boolean isEmpty() {
    return extensions.isEmpty();
  }

}
