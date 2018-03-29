package io.committed.invest.plugins.ui.host.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class InvestHostedUiExtensionsTest {

  @Test
  public void testEmpty() {
    final InvestHostedUiExtensions e = new InvestHostedUiExtensions(Collections.emptyList());

    assertThat(e.getExtensions()).isEmpty();
    assertThat(e.isEmpty()).isTrue();
  }

  @Test
  public void testList() {
    final List<PluginJson> list = Arrays.asList(new PluginJson(), new PluginJson());
    final InvestHostedUiExtensions e = new InvestHostedUiExtensions(list);

    assertThat(e.getExtensions()).containsExactlyElementsOf(list);
    assertThat(e.isEmpty()).isFalse();
  }
}
