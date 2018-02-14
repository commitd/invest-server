package io.committed.invest.server.data.testing;

import java.util.Arrays;
import java.util.List;
import io.committed.invest.extensions.data.dataset.Dataset;
import io.committed.invest.extensions.data.providers.DataProvider;


public final class Fixtures {

  private Fixtures() {

  }

  public static List<Dataset> createDatasets() {
    return Arrays.asList(Dataset.builder().id("a").name("A").description("A dataset").build(),
        Dataset.builder().id("b").name("B").description("B dataset").build());
  }

  public static List<DataProvider> createProviders() {
    return Arrays.asList(new FakeDataProvider());
  }
}
