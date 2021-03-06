package io.committed.invest.server.data.testing;

import java.util.Map;

import reactor.core.publisher.Mono;

import io.committed.invest.extensions.data.providers.AbstractDataProviderFactory;

public class FakeDataProviderFactory extends AbstractDataProviderFactory<FakeDataProvider> {

  public static final String ID = "fake-1";
  private final FakeDataProvider fdp;

  public FakeDataProviderFactory() {
    this(new FakeDataProvider());
  }

  public FakeDataProviderFactory(final FakeDataProvider fdp) {
    super(ID, FakeDataProvider.class, "test-datasource");
    this.fdp = fdp;
  }

  @Override
  public Mono<FakeDataProvider> build(
      final String dataset, final String datasource, final Map<String, Object> settings) {
    return Mono.justOrEmpty(fdp);
  }
}
