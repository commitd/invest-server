package io.committed.invest.support.data.mongo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import org.junit.Test;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import reactor.core.publisher.Mono;

public class AbstractMongoRepositoryDataProviderTest {

  @Test
  public void test() {
    final MongoDatabase db = mock(MongoDatabase.class);
    final MongoCollection collection = mock(MongoCollection.class);
    doReturn(collection).when(db).getCollection("collectionName");

    final AbstractMongoRepositoryDataProvider dp =
        new AbstractMongoRepositoryDataProvider("dataset", "datasource", db, "collectionName") {

          @Override
          public String getProviderType() {
            return null;
          }

        };

    assertThat(dp.getCollection()).isSameAs(collection);
    assertThat(dp.getCollectionName()).isSameAs("collectionName");

    doReturn(Mono.just(50)).when(collection).count();
    assertThat(dp.count().block()).isEqualTo(50);

  }

}
