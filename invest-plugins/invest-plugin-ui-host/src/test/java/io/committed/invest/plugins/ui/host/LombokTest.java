package io.committed.invest.plugins.ui.host;

import static org.mockito.Mockito.mock;
import org.junit.Test;
import org.springframework.core.io.Resource;
import io.committed.invest.plugins.ui.host.data.PluginJson;
import io.committed.invest.plugins.ui.host.data.PluginOverride;
import io.committed.invest.test.BeanTestSupport;
import io.committed.invest.test.LombokDataTestSupport;

public class LombokTest {

  @Test
  public void testLombok() {
    BeanTestSupport.addFactory(Resource.class, () -> mock(Resource.class));

    final LombokDataTestSupport mt = new LombokDataTestSupport();
    mt.testClass(UiHostSettings.class);
    mt.testClasses(PluginJson.class, PluginOverride.class);
  }

}
