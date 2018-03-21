package io.committed.invest.support.data.mongo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.Arrays;
import java.util.List;
import org.bson.conversions.Bson;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import com.mongodb.reactivestreams.client.AggregatePublisher;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import lombok.Data;
import reactor.core.publisher.Mono;

public class AbstractMongoCollectionDataProviderTest {

  private StubMongoCollectionDataProvider dp;
  private MongoCollection<Item> itemCollection;
  private MongoDatabase db;


  @Before
  public void before() {
    db = mock(MongoDatabase.class);
    db.getCollection("items", Item.class);

    itemCollection = mock(MongoCollection.class);
    doReturn(itemCollection).when(db).getCollection("collectionName", Item.class);
    doReturn(itemCollection).when(db).getCollection("collectionName");

    dp = new StubMongoCollectionDataProvider(db);
  }

  @Test
  public void testCollection() {
    assertThat(dp.getCollectionName()).isEqualTo("collectionName");
    assertThat(dp.getCollection()).isEqualTo(itemCollection);
  }

  @Test
  public void testFindByField() {
    final FindPublisher<Item> publisher = mock(FindPublisher.class);
    doReturn(publisher).when(itemCollection).find(ArgumentMatchers.any(Bson.class));

    dp.findByField("field", "value");

    // There's no equals on the filters so it dones't match when we pass same filter.eq in.
    verify(itemCollection).find(ArgumentMatchers.any(Bson.class));
  }

  @Test
  public void testFindAllByField() {
    final FindPublisher<Item> publisher = mock(FindPublisher.class);
    doReturn(publisher).when(itemCollection).find(ArgumentMatchers.any(Bson.class));

    dp.findAllByField("field", "value");

    // There's no equals on the filters so it dones't match when we pass same filter.eq in.
    verify(itemCollection).find(ArgumentMatchers.any(Bson.class));

  }

  @Test
  public void testFindAll() {
    final FindPublisher<Item> publisher = mock(FindPublisher.class);
    doReturn(publisher).when(publisher).skip(ArgumentMatchers.anyInt());
    doReturn(publisher).when(publisher).skip(ArgumentMatchers.anyInt());
    doReturn(publisher).when(itemCollection).find();

    dp.findAll(7, 10);
  }

  @Test
  public void testAggregate() {

    final List<Bson> pipeline = Arrays.asList();

    final AggregatePublisher<Item> publisher = mock(AggregatePublisher.class);
    doReturn(publisher).when(itemCollection).aggregate(pipeline, AggOutput.class);

    dp.aggregate(pipeline, AggOutput.class);

    verify(itemCollection).aggregate(pipeline, AggOutput.class);

  }


  @Test
  public void testCount() {

    doReturn(Mono.just(5)).when(itemCollection).count();

    assertThat(dp.count().block()).isEqualTo(5);
  }

  public class StubMongoCollectionDataProvider extends AbstractMongoCollectionDataProvider {

    protected StubMongoCollectionDataProvider(final MongoDatabase mongoDatabase) {
      super("dataset", "datasource", mongoDatabase, "collectionName", Item.class);
    }

    @Override
    public String getProviderType() {
      return null;
    }
  }

  @Data
  public static class Item {
    private String name;
  }

  @Data
  public static class AggOutput {
    private int count;
  }
}
