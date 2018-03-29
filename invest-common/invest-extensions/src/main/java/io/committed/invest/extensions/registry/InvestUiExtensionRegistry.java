package io.committed.invest.extensions.registry;

import java.util.Collection;

import io.committed.invest.extensions.InvestUiExtension;
import io.committed.invest.extensions.collections.InvestExtensions;

/** A registry of Invest UI extension within the application */
public class InvestUiExtensionRegistry extends AbstractInvestExtensionRegistry<InvestUiExtension> {

  public InvestUiExtensionRegistry(final Collection<InvestUiExtension> extensions) {
    super(extensions);
  }

  public static InvestUiExtensionRegistry create(
      final Collection<InvestUiExtension> extensions,
      final Collection<InvestExtensions> collections) {
    final Collection<InvestUiExtension> all =
        AbstractInvestExtensionRegistry.combine(extensions, collections, InvestUiExtension.class);

    return new InvestUiExtensionRegistry(all);
  }
}
