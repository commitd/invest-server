package io.committed.vessel.server.data.testing;

import java.util.Arrays;
import java.util.List;

import io.committed.vessel.server.data.dataset.Dataset;
import io.committed.vessel.server.data.providers.DataProvider;

public final class Fixtures {

  private Fixtures() {

  }

  public static List<Dataset> createDatasets() {
    return Arrays.asList(
        Dataset.builder().id("a").name("A").description("A dataset").build(),
        Dataset.builder().id("b").name("B").description("B dataset").build());
  }

  public static List<DataProvider> createProviders() {
    return Arrays.asList(
        new FakeDataProvider());
  }
}
