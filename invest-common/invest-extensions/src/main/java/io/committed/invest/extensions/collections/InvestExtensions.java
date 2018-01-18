package io.committed.invest.extensions.collections;

import java.util.Collection;
import java.util.stream.Stream;
import io.committed.invest.extensions.InvestExtension;
import lombok.Data;

/**
 * A generic collection for invest extension.
 *
 * Anyone can create this to hold any type of extension.
 *
 */
@Data
public class InvestExtensions {

  private final Collection<? extends InvestExtension> extensions;

  public boolean isEmpty() {
    return extensions == null || extensions.isEmpty();
  }

  public Stream<? extends InvestExtension> stream() {
    return extensions == null ? Stream.empty() : extensions.stream();
  }

  public <E extends InvestExtension> Stream<E> stream(final Class<E> clazz) {
    return stream()
        .filter(clazz::isInstance)
        .map(clazz::cast);
  }
}
