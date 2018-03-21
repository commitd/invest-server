package io.committed.invest.support.data.mongo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.committed.invest.extensions.data.providers.DatabaseConstants;

public class AbstractMongoDataProviderTest {

  @Test
  public void test() {
    final MongoDatabase db = mock(MongoDatabase.class);

    final AbstractMongoDataProvider dp = new AbstractMongoDataProvider("dataset", "datasource", db) {

      @Override
      public String getProviderType() {
        return null;
      }

    };

    assertThat(dp.getDataset()).isEqualTo("dataset");
    assertThat(dp.getDatasource()).isEqualTo("datasource");
    assertThat(dp.getMongoDatabase()).isSameAs(db);
    assertThat(dp.getDatabase()).isEqualTo(DatabaseConstants.MONGO);

    dp.getCollection("test");
    verify(db).getCollection("test");

    dp.getCollection("test2", Object.class);
    verify(db).getCollection("test2", Object.class);

  }

}
