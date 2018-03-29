package io.committed.invest.support.data.mongo;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import reactor.core.publisher.Mono;

import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;

public class AbstractMongoCrudDataProviderTest {

  private StubMongoCrudDataProvider dp;
  private MongoDatabase db;
  private MongoCollection<Replacement> replacementCollection;
  private MongoCollection<Document> documentCollection;

  @Before
  public void before() {
    db = mock(MongoDatabase.class);
    replacementCollection = mock(MongoCollection.class);
    documentCollection = mock(MongoCollection.class);

    doReturn(replacementCollection).when(db).getCollection("collectionName", Replacement.class);
    doReturn(documentCollection).when(db).getCollection("collectionName");
    dp = new StubMongoCrudDataProvider(db);
  }

  @Test
  public void testReplaceStringBsonDocument() {
    final Document filter = new Document();
    final Document replacement = new Document();
    doReturn(Mono.just(new Document()))
        .when(documentCollection)
        .findOneAndReplace(
            ArgumentMatchers.eq(filter),
            ArgumentMatchers.eq(replacement),
            ArgumentMatchers.any(FindOneAndReplaceOptions.class));

    dp.replace("collectionName", filter, replacement);

    verify(documentCollection)
        .findOneAndReplace(
            ArgumentMatchers.eq(filter),
            ArgumentMatchers.eq(replacement),
            ArgumentMatchers.any(FindOneAndReplaceOptions.class));
  }

  @Test
  public void testReplaceStringBsonSClassOfS() {
    final Replacement r = new Replacement();
    final Document filter = new Document();
    doReturn(Mono.just(new Document()))
        .when(replacementCollection)
        .findOneAndReplace(
            ArgumentMatchers.eq(filter),
            ArgumentMatchers.eq(r),
            ArgumentMatchers.any(FindOneAndReplaceOptions.class));

    dp.replace("collectionName", filter, r, Replacement.class);

    verify(replacementCollection)
        .findOneAndReplace(
            ArgumentMatchers.eq(filter),
            ArgumentMatchers.eq(r),
            ArgumentMatchers.any(FindOneAndReplaceOptions.class));
  }

  @Test
  public void testUpdate() {
    final Document filter = new Document();
    final Document update = new Document();
    doReturn(Mono.just(new Document()))
        .when(documentCollection)
        .findOneAndUpdate(ArgumentMatchers.eq(filter), ArgumentMatchers.eq(update));

    dp.update("collectionName", new Document(), new Document());

    verify(documentCollection)
        .findOneAndUpdate(ArgumentMatchers.eq(filter), ArgumentMatchers.eq(update));
  }

  @Test
  public void testDelete() {
    final Document filter = new Document();
    final DeleteResult deleteResult = mock(DeleteResult.class);
    doReturn(Mono.just(deleteResult))
        .when(documentCollection)
        .deleteMany(ArgumentMatchers.eq(filter));

    dp.delete("collectionName", filter);

    verify(documentCollection).deleteMany(ArgumentMatchers.eq(filter));
  }

  public static class StubMongoCrudDataProvider
      extends AbstractMongoCrudDataProvider<String, Object> {

    protected StubMongoCrudDataProvider(final MongoDatabase mongoDatabase) {
      super("dataset", "datasource", mongoDatabase);
    }

    private List<String> deleted = new ArrayList<>();
    private List<Object> saved = new ArrayList<>();

    @Override
    public boolean delete(final String reference) {
      deleted.add(reference);
      return false;
    }

    @Override
    public boolean save(final Object item) {
      saved.add(item);
      return true;
    }

    @Override
    public String getProviderType() {
      return null;
    }
  }

  @Data
  public static class Replacement {
    private String name;
  }
}
