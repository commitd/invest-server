package io.committed.invest.plugins.ui.application;

import org.junit.Test;

import io.committed.invest.test.LombokDataTestSupport;

public class LombokTest {

  @Test
  public void testLombok() {
    final LombokDataTestSupport mt = new LombokDataTestSupport();
    mt.testClass(ApplicationSettings.class);
    mt.testClass(UiApplicationSettings.class);
  }
}
