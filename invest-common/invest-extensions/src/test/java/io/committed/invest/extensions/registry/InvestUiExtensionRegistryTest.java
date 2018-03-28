package io.committed.invest.extensions.registry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import io.committed.invest.extensions.InvestUiExtension;
import io.committed.invest.extensions.collections.InvestExtensions;

public class InvestUiExtensionRegistryTest {

  @Test
  public void testEmpty() {
    final InvestUiExtensionRegistry extensions =
        new InvestUiExtensionRegistry(Collections.emptyList());

    assertThat(extensions.stream()).isEmpty();
    assertThat(extensions.getExtensions()).hasSize(0);

    assertThat(extensions.isEmpty()).isTrue();
  }

  @Test
  public void test() {

    final InvestUiExtension ie1 = mock(InvestUiExtension.class);
    final InvestUiExtension ie2 = mock(InvestUiExtension.class);
    final InvestUiExtension ie3 = mock(InvestUiExtension.class);

    final InvestUiExtensionRegistry extensions =
        new InvestUiExtensionRegistry(Arrays.asList(ie1, ie2, ie3));

    assertThat(extensions.stream()).contains(ie1, ie2, ie3);

    assertThat(extensions.isEmpty()).isFalse();
  }

  @Test
  public void testCreate() {

    final InvestUiExtension ie1 = mock(InvestUiExtension.class);
    final InvestUiExtension ie2 = mock(InvestUiExtension.class);
    final InvestUiExtension ie3 = mock(InvestUiExtension.class);

    final InvestUiExtensionRegistry extensions =
        InvestUiExtensionRegistry.create(
            Arrays.asList(ie1), Arrays.asList(new InvestExtensions(Arrays.asList(ie2, ie3))));

    assertThat(extensions.stream()).contains(ie1, ie2, ie3);
  }
}
