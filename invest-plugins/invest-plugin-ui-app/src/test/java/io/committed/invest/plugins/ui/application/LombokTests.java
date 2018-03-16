package io.committed.invest.plugins.ui.application;

import org.junit.Test;
import io.committed.invest.plugins.ui.application.ApplicationSettings;
import io.committed.invest.plugins.ui.application.UiApplicationSettings;
import io.committed.invest.test.LombokDataTestSupport;

public class LombokTests {

  @Test
  public void testLombok() {
    final LombokDataTestSupport mt = new LombokDataTestSupport();
    mt.testClass(ApplicationSettings.class);
    mt.testClass(UiApplicationSettings.class);
  }

}
