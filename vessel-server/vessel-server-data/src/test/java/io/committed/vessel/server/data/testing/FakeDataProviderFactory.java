package io.committed.vessel.server.data.testing;

import java.util.Map;

import io.committed.vessel.server.data.providers.AbstractDataProviderFactory;
import reactor.core.publisher.Mono;

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
  public Mono<FakeDataProvider> build(final String corpus, final Map<String, Object> settings) {
    return Mono.justOrEmpty(fdp);
  }

}
