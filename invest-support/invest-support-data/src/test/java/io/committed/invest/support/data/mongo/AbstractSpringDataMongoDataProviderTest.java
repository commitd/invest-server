package io.committed.invest.support.data.mongo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import org.junit.Test;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import io.committed.invest.extensions.data.providers.DatabaseConstants;

public class AbstractSpringDataMongoDataProviderTest {

  @Test
  public void test() {
    final ReactiveMongoTemplate mongo = mock(ReactiveMongoTemplate.class);

    final AbstractSpringDataMongoDataProvider dp =
        new AbstractSpringDataMongoDataProvider("dataset", "datasource", mongo) {

          @Override
          public String getProviderType() {
            return null;
          }

        };


    assertThat(dp.getDatabase()).isEqualTo(DatabaseConstants.MONGO);
    assertThat(dp.getDataset()).isEqualTo("dataset");
    assertThat(dp.getDatasource()).isEqualTo("datasource");
    assertThat(dp.getTemplate()).isSameAs(mongo);
  }

}
