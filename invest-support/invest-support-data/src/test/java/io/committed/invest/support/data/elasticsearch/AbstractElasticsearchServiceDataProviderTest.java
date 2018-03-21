package io.committed.invest.support.data.elasticsearch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import org.junit.Test;

public class AbstractElasticsearchServiceDataProviderTest {

  @Test
  public void testGetService() {


    final ElasticsearchSupportService ess = mock(ElasticsearchSupportService.class);
    final AbstractElasticsearchServiceDataProvider<Object, ElasticsearchSupportService<Object>> dp =
        new AbstractElasticsearchServiceDataProvider("dataset", "datasource", ess) {
          @Override
          public String getProviderType() {
            return null;
          }
        };

    assertThat(dp.getService()).isSameAs(ess);
  }

}
