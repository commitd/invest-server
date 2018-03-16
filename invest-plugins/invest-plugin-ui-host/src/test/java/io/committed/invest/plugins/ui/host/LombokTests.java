package io.committed.invest.plugins.ui.host;

import org.junit.Test;
import io.committed.invest.plugins.ui.host.data.PluginJson;
import io.committed.invest.plugins.ui.host.data.PluginOverride;
import io.committed.invest.test.LombokDataTestSupport;

public class LombokTests {

  @Test
  public void testLombok() {
    final LombokDataTestSupport mt = new LombokDataTestSupport();
    mt.testClass(UiHostSettings.class);
    mt.testClasses(PluginJson.class, PluginOverride.class);
  }

}
