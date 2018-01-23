package io.committed.invest.extensions.registry;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import io.committed.invest.extensions.InvestUiExtension;
import io.committed.invest.extensions.collections.InvestExtensions;

public class InvestUiExtensionRegistry extends AbstractInvestExtensionRegistry<InvestUiExtension> {

  public InvestUiExtensionRegistry(final Collection<InvestUiExtension> extensions) {
    super(extensions);
  }

  public static InvestUiExtensionRegistry create(final Collection<InvestUiExtension> extensions,
      final Collection<InvestExtensions> collections) {
    final List<InvestUiExtension> all = new LinkedList<InvestUiExtension>();

    all.addAll(extensions);
    collections.forEach(a -> a.stream(InvestUiExtension.class).forEach(all::add));

    return new InvestUiExtensionRegistry(all);
  }

}
