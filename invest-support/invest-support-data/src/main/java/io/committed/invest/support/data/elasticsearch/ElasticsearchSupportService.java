package io.committed.invest.support.data.elasticsearch;

import java.time.Instant;
import java.util.Optional;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.support.ValueType;
import org.joda.time.DateTime;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.committed.invest.core.dto.analytic.TermBin;
import io.committed.invest.core.dto.analytic.TimeBin;
import io.committed.invest.core.dto.constants.TimeInterval;
import io.committed.invest.support.elasticsearch.utils.ReactiveElasticsearchUtils;
import io.committed.invest.support.elasticsearch.utils.SourceUtils;
import io.committed.invest.support.elasticsearch.utils.TimeIntervalUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ElasticsearchSupportService<E> {

  private final Client client;
  private final ObjectMapper mapper;
  private final String index;
  private final String type;
  private final Class<E> entityClazz;

  public ElasticsearchSupportService(final Client client, final ObjectMapper mapper,
      final String index, final String type, final Class<E> entityClazz) {
    this.client = client;
    this.mapper = mapper;
    this.index = index;
    this.type = type;
    this.entityClazz = entityClazz;
  }

  protected Client getClient() {
    return client;
  }

  protected ObjectMapper getMapper() {
    return mapper;
  }

  protected String getIndex() {
    return index;
  }

  protected String getType() {
    return type;
  }

  protected Class<E> getEntityClass() {
    return entityClazz;
  }

  public Flux<E> searchByQuery(final String search, final int offset, final int limit) {
    return search(QueryBuilders.queryStringQuery(search), offset, limit);
  }

  public Flux<E> search(final QueryBuilder query, final int offset, final int limit) {
    final ListenableActionFuture<SearchResponse> future = getClient().prepareSearch()
        .setIndices(index)
        .setTypes(type)
        .setQuery(query)
        .setFrom(offset)
        .setSize(limit)
        .execute();

    return ReactiveElasticsearchUtils.toMono(future)
        .flatMapMany(r -> SourceUtils.convertHits(getMapper(), r, entityClazz));
  }

  public <A> Mono<Aggregations> aggregation(final Optional<QueryBuilder> query, final AggregationBuilder aggregation) {
    final SearchRequestBuilder requestBuilder = getClient().prepareSearch()
        .setIndices(index)
        .setTypes(type)
        .setFrom(0)
        .setSize(0)
        .addAggregation(aggregation);

    query.ifPresent(requestBuilder::setQuery);

    final ListenableActionFuture<SearchResponse> future = requestBuilder.execute();

    return ReactiveElasticsearchUtils.toMono(future)
        .map(r -> r.getAggregations());
  }



  public Flux<TimeBin> timelineAggregation(final Optional<QueryBuilder> q, final String field,
      final TimeInterval interval) {
    final AggregationBuilder ab = AggregationBuilders
        .filter("filtered", q.orElse(QueryBuilders.matchAllQuery()))
        .subAggregation(AggregationBuilders.dateHistogram("agg")
            .field(field)
            .dateHistogramInterval(TimeIntervalUtils.toDateHistogram(interval)));

    return aggregation(Optional.empty(), ab)
        .flatMapMany(r -> {
          final Filter filtered = r.get("filtered");
          final Histogram terms = filtered.getAggregations().get("agg");
          return Flux.fromIterable(terms.getBuckets()).map(b -> {
            final Instant i = Instant.ofEpochMilli(((DateTime) b.getKey()).toInstant().getMillis());
            return new TimeBin(i, b.getDocCount());
          });
        });

  }



  public Flux<TermBin> termAggregation(final Optional<QueryBuilder> q, final String field, final int size) {
    final AggregationBuilder ab = AggregationBuilders
        .filter("filtered", q.orElse(QueryBuilders.matchAllQuery()))
        .subAggregation(AggregationBuilders.terms("agg")
            .valueType(ValueType.STRING)
            .field(field + ".keyword")
            .size(size));

    return aggregation(Optional.empty(), ab)
        .flatMapMany(r -> {
          final Filter filtered = r.get("filtered");
          final Terms terms = filtered.getAggregations().get("agg");
          return Flux.fromIterable(terms.getBuckets())
              .map(b -> new TermBin(b.getKeyAsString(), b.getDocCount()));
        });
  }

  public Mono<Long> count() {
    return count(QueryBuilders.matchAllQuery());
  }

  public Mono<Long> count(final QueryBuilder query) {
    // Count is a search with size 0
    final ListenableActionFuture<SearchResponse> future = getClient().prepareSearch()
        .setIndices(index)
        .setTypes(type)
        .setQuery(query)
        .setFrom(0)
        .setSize(0)
        .execute();

    return ReactiveElasticsearchUtils.toMono(future)
        .map(r -> r.getHits().getTotalHits());
  }

  public Flux<E> getAll(final int offset, final int limit) {
    return search(QueryBuilders.matchAllQuery(), offset, limit);
  }

  public Mono<E> getById(final String id) {
    final ListenableActionFuture<GetResponse> future =
        getClient().prepareGet()
            .setIndex(index)
            .setType(type)
            .setId(id)
            .execute();

    return ReactiveElasticsearchUtils.toMono(future)
        .flatMap(r -> SourceUtils.convertSource(getMapper(), r.getSourceAsString(), entityClazz));
  }



}
