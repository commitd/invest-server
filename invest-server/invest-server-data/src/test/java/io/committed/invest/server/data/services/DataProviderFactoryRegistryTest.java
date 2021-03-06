package io.committed.invest.server.data.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.google.common.collect.Maps;

import io.committed.invest.extensions.data.dataset.DataProviderSpecification;
import io.committed.invest.extensions.data.providers.DataProvider;
import io.committed.invest.extensions.data.providers.DataProviderFactory;
import io.committed.invest.server.data.testing.AnotherFakeDataProvider;
import io.committed.invest.server.data.testing.AnotherFakeDataProviderFactory;
import io.committed.invest.server.data.testing.FakeDataProvider;
import io.committed.invest.server.data.testing.FakeDataProviderFactory;

public class DataProviderFactoryRegistryTest {

  private DataProviderFactoryRegistry registry;

  @Before
  public void setUp() {
    registry =
        new DataProviderFactoryRegistry(
            Arrays.asList(new FakeDataProviderFactory(), new AnotherFakeDataProviderFactory(null)));
  }

  @Test
  public void testFindFactoryWhereExists() {
    final Flux<DataProviderFactory<DataProvider>> factories =
        registry.findFactories(FakeDataProviderFactory.ID);

    final List<DataProviderFactory<DataProvider>> block = factories.collectList().block();
    assertThat(block).hasSize(1);
    assertThat(block.get(0).getId()).isEqualTo(FakeDataProviderFactory.ID);
  }

  @Test
  public void testFindFactoryWhereMissing() {
    final Flux<DataProviderFactory<DataProvider>> factories = registry.findFactories("missing");

    final List<DataProviderFactory<DataProvider>> block = factories.collectList().block();
    assertThat(block).hasSize(0);
  }

  @Test
  public void testFindFactoriesWithIdAndClassWhereMatch() {
    final Flux<DataProviderFactory<FakeDataProvider>> factories =
        registry.findFactories(FakeDataProviderFactory.ID, FakeDataProvider.class);

    final List<DataProviderFactory<FakeDataProvider>> block = factories.collectList().block();
    assertThat(block).hasSize(1);
    assertThat(block.get(0).getId()).isEqualTo(FakeDataProviderFactory.ID);
  }

  @Test
  public void testFindFactoriesWithIdAndClassWhereMisMatch() {
    final Flux<DataProviderFactory<AnotherFakeDataProvider>> factories =
        registry.findFactories(FakeDataProviderFactory.ID, AnotherFakeDataProvider.class);

    final List<DataProviderFactory<AnotherFakeDataProvider>> block =
        factories.collectList().block();
    assertThat(block).hasSize(0);
  }

  @Test
  public void testBuildWhereMatches() {
    final Mono<? extends DataProvider> mono =
        registry.build(
            "test-dataset",
            DataProviderSpecification.builder()
                .factory(FakeDataProviderFactory.ID)
                .datasource("test-datasource")
                .settings(Maps.newHashMap())
                .build());
    assertThat(mono.block()).isInstanceOf(FakeDataProvider.class);
  }

  @Test
  public void testBuildWhereFails() {
    final Mono<? extends DataProvider> mono =
        registry.build(
            "test-dataset",
            DataProviderSpecification.builder()
                .factory(AnotherFakeDataProviderFactory.ID)
                .datasource("test-datasource")
                .settings(Maps.newHashMap())
                .build());
    assertThat(mono.hasElement().block()).isFalse();
  }
}
