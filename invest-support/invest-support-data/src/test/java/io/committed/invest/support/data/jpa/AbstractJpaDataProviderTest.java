package io.committed.invest.support.data.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import io.committed.invest.extensions.data.providers.DatabaseConstants;

public class AbstractJpaDataProviderTest {

  @Test
  public void testGetDatabase() {
    final AbstractJpaDataProvider dp =
        new AbstractJpaDataProvider("dataset", "datasource") {

          @Override
          public String getProviderType() {
            return null;
          }
        };

    assertThat(dp.getDatabase()).isEqualTo(DatabaseConstants.SQL);
    assertThat(dp.getDataset()).isEqualTo("dataset");
    assertThat(dp.getDatasource()).isEqualTo("datasource");
  }
}
