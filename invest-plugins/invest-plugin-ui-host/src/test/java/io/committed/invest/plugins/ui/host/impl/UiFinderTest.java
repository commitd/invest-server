package io.committed.invest.plugins.ui.host.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import io.committed.invest.extensions.collections.InvestExtensions;
import io.committed.invest.plugins.ui.host.UiHostSettings;
import io.committed.invest.plugins.ui.host.data.PluginJson;

public class UiFinderTest {

  @Test
  public void testWithDefaultConfig() throws IOException {
    final PluginFinder pf = mock(PluginFinder.class);
    final UiFinder finder = new UiFinder(new UiHostSettings(), pf);


    final List<PluginJson> plugins = Arrays.asList(
        new PluginJson(),
        new PluginJson(),
        new PluginJson());
    doReturn(plugins.stream()).when(pf).readPluginsFromDirectory(ArgumentMatchers.any());

    final InvestExtensions uiHostedExtensions = finder.uiHostedExtensions();

    assertThat(uiHostedExtensions.getExtensions()).hasSize(3);
  }

}
