package io.committed.invest.support.data.mongo;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.Optional;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.mongodb.reactivestreams.client.Success;
import lombok.Data;
import reactor.core.publisher.Mono;

public class AbstractMongoCrudCollectionDataProviderTest {

  private StubMongoCrudCollectionDataProvider dp;
  private MongoCollection<SavedItem> savedCollection;

  private MongoDatabase db;

  @Before
  public void before() {
    db = mock(MongoDatabase.class);
    savedCollection = mock(MongoCollection.class);
    doReturn(savedCollection).when(db).getCollection("collectionName", SavedItem.class);

    dp = new StubMongoCrudCollectionDataProvider(db);

  }

  @Test
  public void testDelete() {
    final DeleteResult deleteResults = mock(DeleteResult.class);
    doReturn(Mono.just(deleteResults)).when(savedCollection).deleteMany(ArgumentMatchers.any());

    dp.delete("123");

    verify(savedCollection).deleteMany(new Document("id", "123"));

  }

  @Test
  public void testSave() {
    doReturn(Mono.just(Success.SUCCESS)).when(savedCollection).insertOne(ArgumentMatchers.any());

    final SavedItem i = new SavedItem();
    dp.save(i);

    verify(savedCollection).insertOne(i);

  }


  public static class StubMongoCrudCollectionDataProvider
      extends AbstractMongoCrudCollectionDataProvider<String, SavedItem, SavedItem> {

    protected StubMongoCrudCollectionDataProvider(final MongoDatabase mongoDatabase) {
      super("dataset", "datasource", mongoDatabase, "collectionName", SavedItem.class);
      // TODO Auto-generated constructor stub
    }

    @Override
    public String getProviderType() {
      return null;
    }

    @Override
    protected Optional<Bson> filter(final String r) {
      return Optional.of(new Document("id", r));
    }

    @Override
    protected Optional<SavedItem> convert(final SavedItem t) {
      return Optional.ofNullable(t);
    }
  }

  @Data
  public class SavedItem {
    private String externalId;
  }
}
