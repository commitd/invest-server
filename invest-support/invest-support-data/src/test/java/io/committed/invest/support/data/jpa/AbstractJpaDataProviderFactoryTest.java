package io.committed.invest.support.data.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import io.committed.invest.extensions.data.providers.DataProvider;
import reactor.core.publisher.Mono;

public class AbstractJpaDataProviderFactoryTest {

  @Test
  public void testDefaults() {
    final Map<String, Object> settings = Collections.emptyMap();

    final StubJpaDataProviderFactory dpf = new StubJpaDataProviderFactory();
    dpf.build("dataset", "datasource", settings);

    // TODO: Not sure hwo to inspec this further?
    assertThat(dpf.dataSourceBuilder).isNotNull();

  }

  @Test
  public void testWithSettings() {
    final Map<String, Object> settings = new HashMap<>();
    settings.put(AbstractJpaDataProviderFactory.DRIVER_CLASS_NAME, "class");
    settings.put(AbstractJpaDataProviderFactory.PASSWORD, "pass");
    settings.put(AbstractJpaDataProviderFactory.USERNAME, "user");
    settings.put(AbstractJpaDataProviderFactory.URL, "url");

    final StubJpaDataProviderFactory dpf = new StubJpaDataProviderFactory();
    dpf.build("dataset", "datasource", settings);

    // TODO: Not sure hwo to inspec this further?
    assertThat(dpf.dataSourceBuilder).isNotNull();

  }

  public static class StubJpaDataProviderFactory extends AbstractJpaDataProviderFactory<DataProvider> {

    private DataSourceBuilder<?> dataSourceBuilder;

    public StubJpaDataProviderFactory() {
      super(null, "id", DataProvider.class, StubJpaDataProviderFactory.class);
    }

    @Override
    public Mono<DataProvider> build(final String dataset, final String datasource, final Map<String, Object> settings) {

      buildRepositoryFactory(settings);
      return Mono.just(mock(DataProvider.class));
    }

    @Override
    protected JpaRepositoryFactory createFromBuilder(final DataSourceBuilder<?> dataSourceBuilder) {
      this.dataSourceBuilder = dataSourceBuilder;
      return mock(JpaRepositoryFactory.class);
    }

  }

}
