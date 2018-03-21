package io.committed.invest.plugins.ui.host.data;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import org.junit.Test;
import io.committed.invest.extensions.actions.SimpleActionDefinition;

public class PluginJsonTest {

  @Test
  public void testNull() {
    final PluginJson p = new PluginJson();

    p.setActions(null);

    assertThat(p.getActions()).isEmpty();
  }

  @Test
  public void testSingle() {
    final PluginJson p = new PluginJson();

    p.setActions(Arrays.asList(
        SimpleActionDefinition.builder()
            .action("test")
            .build()));

    assertThat(p.getActions()).hasSize(1);
  }

}
