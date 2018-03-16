package io.committed.invest.plugins.ui.host;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import io.committed.invest.plugins.ui.host.data.PluginOverride;
import io.committed.invest.test.BeanTestSupport;
import io.committed.invest.test.LombokDataTestSupport;

public class LombokTest {

  @Test
  public void testLombok() {
    BeanTestSupport.addFactory(Resource.class, () -> new ClassPathResource("/fake"));

    final LombokDataTestSupport mt = new LombokDataTestSupport();
    mt.testClass(UiHostSettings.class);
    // TODO: Strange issues with PluginJson.class,
    mt.testClasses(PluginOverride.class);
  }

}
