package io.committed.invest.server.data.testing;

import java.util.Map;
import io.committed.invest.server.data.providers.AbstractDataProviderFactory;
import reactor.core.publisher.Mono;

public class AnotherFakeDataProviderFactory
    extends AbstractDataProviderFactory<AnotherFakeDataProvider> {

  public static final String ID = "fake-2";
  private final AnotherFakeDataProvider fdp;

  public AnotherFakeDataProviderFactory() {
    this(new AnotherFakeDataProvider());
  }

  public AnotherFakeDataProviderFactory(final AnotherFakeDataProvider fdp) {
    super(ID, AnotherFakeDataProvider.class, "test-datasource");
    this.fdp = fdp;
  }

  @Override
  public Mono<AnotherFakeDataProvider> build(final String dataset, final String datasource,
      final Map<String, Object> settings) {
    return Mono.justOrEmpty(fdp);
  }

}
