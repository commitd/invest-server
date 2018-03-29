package io.committed.invest.support.data.elasticsearch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import lombok.Data;

import org.elasticsearch.action.delete.DeleteAction;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetAction;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchAction;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.committed.invest.support.elasticsearch.utils.StubListenableActionFuture;

public class ElasticsearchSupportServiceTest {

  private static final String TYPE = "type";
  private static final String INDEX = "index";

  @Data
  public static class Entity {
    private String value;
  }

  private Client client;
  private ElasticsearchSupportService<Entity> ess;
  private ObjectMapper mapper;

  @Before
  public void before() {
    client = mock(Client.class);
    mapper = new ObjectMapper();
    ess = new ElasticsearchSupportService<>(client, mapper, INDEX, TYPE, Entity.class);
  }

  @Test
  public void testGetClient() {
    assertThat(ess.getClient()).isSameAs(client);
  }

  @Test
  public void testGetMapper() {
    assertThat(ess.getMapper()).isSameAs(mapper);
  }

  @Test
  public void testGetIndex() {
    assertThat(ess.getIndex()).isSameAs(INDEX);
  }

  @Test
  public void testGetType() {
    assertThat(ess.getType()).isSameAs(TYPE);
  }

  @Test
  public void testGetEntityClass() {
    assertThat(ess.getEntityClass()).isEqualTo(Entity.class);
  }

  @Test
  public void testSearchByQuery() {
    final SearchRequestBuilder searchRequestBuilder =
        spy(new SearchRequestBuilder(client, SearchAction.INSTANCE));
    when(client.prepareSearch()).thenReturn(searchRequestBuilder);
    doReturn(new StubListenableActionFuture<SearchResponse>(new SearchResponse()))
        .when(searchRequestBuilder)
        .execute();

    ess.searchByQuery("query string", 5, 18);

    final ArgumentCaptor<QueryBuilder> queryCaptor = ArgumentCaptor.forClass(QueryBuilder.class);

    verify(searchRequestBuilder).setTypes(TYPE);
    verify(searchRequestBuilder).setIndices(INDEX);
    verify(searchRequestBuilder).setQuery(queryCaptor.capture());
    verify(searchRequestBuilder).setSize(18);
    verify(searchRequestBuilder).setFrom(5);

    final QueryStringQueryBuilder qb = (QueryStringQueryBuilder) queryCaptor.getValue();
    assertThat(qb.queryString()).isEqualTo("query string");

    verify(searchRequestBuilder).execute();
  }

  @Test
  public void testSearch() {
    final SearchRequestBuilder searchRequestBuilder =
        spy(new SearchRequestBuilder(client, SearchAction.INSTANCE));
    when(client.prepareSearch()).thenReturn(searchRequestBuilder);
    doReturn(new StubListenableActionFuture<SearchResponse>(new SearchResponse()))
        .when(searchRequestBuilder)
        .execute();

    final BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
    ess.search(boolQuery, 5, 18);

    verify(searchRequestBuilder).setTypes(TYPE);
    verify(searchRequestBuilder).setIndices(INDEX);
    verify(searchRequestBuilder).setSize(18);
    verify(searchRequestBuilder).setFrom(5);

    final ArgumentCaptor<QueryBuilder> queryCaptor = ArgumentCaptor.forClass(QueryBuilder.class);
    verify(searchRequestBuilder).setQuery(queryCaptor.capture());
    assertThat(queryCaptor.getValue()).isSameAs(boolQuery);

    verify(searchRequestBuilder).execute();
  }

  @Test
  public void testCount() {
    final SearchRequestBuilder searchRequestBuilder =
        spy(new SearchRequestBuilder(client, SearchAction.INSTANCE));
    when(client.prepareSearch()).thenReturn(searchRequestBuilder);
    doReturn(new StubListenableActionFuture<SearchResponse>(new SearchResponse()))
        .when(searchRequestBuilder)
        .execute();

    ess.count();

    verify(searchRequestBuilder).setTypes(TYPE);
    verify(searchRequestBuilder).setIndices(INDEX);
    verify(searchRequestBuilder).setSize(0);
    verify(searchRequestBuilder).setFrom(0);

    verify(searchRequestBuilder).execute();
  }

  @Test
  public void testCountQueryBuilder() {
    final BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

    final SearchRequestBuilder searchRequestBuilder =
        spy(new SearchRequestBuilder(client, SearchAction.INSTANCE));
    when(client.prepareSearch()).thenReturn(searchRequestBuilder);
    doReturn(new StubListenableActionFuture<SearchResponse>(new SearchResponse()))
        .when(searchRequestBuilder)
        .execute();

    ess.count(boolQuery);

    verify(searchRequestBuilder).setTypes(TYPE);
    verify(searchRequestBuilder).setIndices(INDEX);
    verify(searchRequestBuilder).setSize(0);
    verify(searchRequestBuilder).setFrom(0);

    final ArgumentCaptor<QueryBuilder> queryCaptor = ArgumentCaptor.forClass(QueryBuilder.class);
    verify(searchRequestBuilder).setQuery(queryCaptor.capture());
    assertThat(queryCaptor.getValue()).isSameAs(boolQuery);

    verify(searchRequestBuilder).execute();
  }

  @Test
  public void testGetAll() {
    final SearchRequestBuilder searchRequestBuilder =
        spy(new SearchRequestBuilder(client, SearchAction.INSTANCE));
    when(client.prepareSearch()).thenReturn(searchRequestBuilder);
    doReturn(new StubListenableActionFuture<SearchResponse>(new SearchResponse()))
        .when(searchRequestBuilder)
        .execute();

    ess.getAll(5, 18);

    verify(searchRequestBuilder).setTypes(TYPE);
    verify(searchRequestBuilder).setIndices(INDEX);
    verify(searchRequestBuilder).setSize(18);
    verify(searchRequestBuilder).setFrom(5);

    final ArgumentCaptor<QueryBuilder> queryCaptor = ArgumentCaptor.forClass(QueryBuilder.class);
    verify(searchRequestBuilder).setQuery(queryCaptor.capture());
    assertThat(queryCaptor.getValue()).isInstanceOf(MatchAllQueryBuilder.class);

    verify(searchRequestBuilder).execute();
  }

  @Test
  public void testGetById() {
    final GetRequestBuilder rb = spy(new GetRequestBuilder(client, GetAction.INSTANCE));
    when(client.prepareGet()).thenReturn(rb);
    doReturn(new StubListenableActionFuture<GetResponse>(new GetResponse(null))).when(rb).execute();

    ess.getById("1234");

    verify(rb).setType(TYPE);
    // Mockito doesn't like this: verify(rb).setIndex(INDEX);
    verify(rb).setId("1234");
    verify(rb).execute();
  }

  @Test
  public void testDeleteById() {

    final DeleteRequestBuilder rb = spy(new DeleteRequestBuilder(client, DeleteAction.INSTANCE));
    doReturn(rb).when(client).prepareDelete();
    doReturn(new DeleteResponse()).when(rb).get();

    ess.deleteById("123");

    verify(rb).setType(TYPE);
    // Mockito doesn't like this: verify(rb).setIndex(INDEX);
    verify(rb).setId("123");
    verify(rb).get();
  }

  @Test
  public void testAggregation() {

    final AvgAggregationBuilder avg = AggregationBuilders.avg("avg");
    final MinAggregationBuilder min = AggregationBuilders.min("min");

    final SearchRequestBuilder searchRequestBuilder =
        spy(new SearchRequestBuilder(client, SearchAction.INSTANCE));
    when(client.prepareSearch()).thenReturn(searchRequestBuilder);
    doReturn(new StubListenableActionFuture<SearchResponse>(new SearchResponse()))
        .when(searchRequestBuilder)
        .execute();

    ess.aggregation(Optional.empty(), min, avg);

    verify(searchRequestBuilder).setTypes(TYPE);
    verify(searchRequestBuilder).setIndices(INDEX);
    verify(searchRequestBuilder).setSize(0);
    verify(searchRequestBuilder).setFrom(0);

    verify(searchRequestBuilder).addAggregation(min);
    verify(searchRequestBuilder).addAggregation(avg);
  }
}
