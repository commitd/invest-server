package io.committed.invest.server.data.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import reactor.core.publisher.Flux;

import io.committed.invest.extensions.data.providers.DataProvider;
import io.committed.invest.extensions.data.providers.DataProviders;
import io.committed.invest.server.data.testing.AnotherFakeDataProvider;
import io.committed.invest.server.data.testing.FakeDataProvider;
import io.committed.invest.server.data.testing.Fixtures;

public class DefaultDatasetProvidersTest {

  @Test
  public void testFindForDataset() {
    final List<DataProvider> providers = Fixtures.createProviders();
    final DataProviders dps = new DefaultDatasetProviders(providers);

    final Flux<DataProvider> dataset = dps.findForDataset(FakeDataProvider.DATASET);

    final List<DataProvider> block = dataset.collectList().block();
    assertThat(block).containsExactly(providers.get(0));
  }

  @Test
  public void testFindForDatasetForMissingDatabset() {
    final List<DataProvider> providers = Fixtures.createProviders();
    final DataProviders dps = new DefaultDatasetProviders(providers);

    final Flux<DataProvider> dataset = dps.findForDataset("missing");

    assertThat(dataset.hasElements().block()).isFalse();
  }

  @Test
  public void testFindForDatasetMatched() {
    final List<DataProvider> providers = Fixtures.createProviders();
    final DefaultDatasetProviders dps = new DefaultDatasetProviders(providers);

    final Flux<FakeDataProvider> dataset =
        dps.findForDataset(FakeDataProvider.DATASET, FakeDataProvider.class);

    final List<FakeDataProvider> block = dataset.collectList().block();
    assertThat(block).containsExactly((FakeDataProvider) providers.get(0));
  }

  @Test
  public void testFindForDatasetNoMatch() {
    final List<DataProvider> providers = Fixtures.createProviders();
    final DefaultDatasetProviders dps = new DefaultDatasetProviders(providers);

    final Flux<AnotherFakeDataProvider> dataset =
        dps.findForDataset(FakeDataProvider.DATASET, AnotherFakeDataProvider.class);

    assertThat(dataset.hasElements().block()).isFalse();
  }

  @Test
  public void testFindForDatasetMatchSuperclass() {
    final List<DataProvider> providers = Fixtures.createProviders();
    final DefaultDatasetProviders dps = new DefaultDatasetProviders(providers);

    final Flux<DataProvider> dataset =
        dps.findForDataset(FakeDataProvider.DATASET, DataProvider.class);

    final List<DataProvider> block = dataset.collectList().block();
    assertThat(block).containsExactly(providers.get(0));
  }
}
