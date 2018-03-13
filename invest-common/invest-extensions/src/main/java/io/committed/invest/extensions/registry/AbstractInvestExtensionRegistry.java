package io.committed.invest.extensions.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import io.committed.invest.extensions.InvestExtension;
import io.committed.invest.extensions.collections.InvestExtensions;

/**
 * A base for building registries for specific Invest extension types.
 *
 * @param <T> the extension type
 */
public abstract class AbstractInvestExtensionRegistry<T extends InvestExtension> {

  private final Collection<T> extensions;

  public AbstractInvestExtensionRegistry(final Collection<T> extensions) {
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

  /**
   * Combined all the extension of the provided class into a single collection
   * 
   * @param extensions
   * @param collections
   * @param clazz
   * @return
   */
  protected static <T extends InvestExtension> Collection<T> combine(final Collection<T> extensions,
      final Collection<InvestExtensions> collections, final Class<T> clazz) {
    final List<T> all = new LinkedList<>();

    if (extensions != null) {
      all.addAll(extensions);
    }
    if (collections != null && !collections.isEmpty()) {
      collections.forEach(a -> a.stream(clazz).forEach(all::add));
    }

    return all;
  }
}
