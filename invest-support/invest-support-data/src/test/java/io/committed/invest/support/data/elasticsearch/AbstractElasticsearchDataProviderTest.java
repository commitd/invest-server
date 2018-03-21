package io.committed.invest.support.data.elasticsearch;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import io.committed.invest.extensions.data.providers.DatabaseConstants;

public class AbstractElasticsearchDataProviderTest {

  @Test
  public void testGetDatabase() {
    final AbstractElasticsearchDataProvider dp = new AbstractElasticsearchDataProvider("dataset", "datasource") {

      @Override
      public String getProviderType() {
        return null;
      }
    };

    assertThat(dp.getDatabase()).isEqualTo(DatabaseConstants.ELASTICSEARCH);
    assertThat(dp.getDataset()).isEqualTo("dataset");
    assertThat(dp.getDatasource()).isEqualTo("datasource");
  }

}
