package io.committed.invest.support.data.elasticsearch;

import java.time.Instant;
import java.util.Optional;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.support.ValueType;
import org.joda.time.DateTime;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.committed.invest.core.dto.analytic.TermBin;
import io.committed.invest.core.dto.analytic.TimeBin;
import io.committed.invest.support.elasticsearch.utils.ReactiveElasticsearchUtils;
import io.committed.invest.support.elasticsearch.utils.SourceUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ElasticsearchSupportService<E> {

  private final Client client;
  private final ObjectMapper mapper;
  private final ElasticsearchTemplate elastic;
  private final String index;
  private final String type;
  private final Class<E> entityClazz;

  public ElasticsearchSupportService(final ObjectMapper mapper, final ElasticsearchTemplate elastic,
      final String index, final String type, final Class<E> entityClazz) {
    this.mapper = mapper;
    this.elastic = elastic;
    this.index = index;
    this.type = type;
    this.entityClazz = entityClazz;
    this.client = elastic.getClient();
  }

  protected Client getClient() {
    return client;
  }

  protected ElasticsearchTemplate getElastic() {
    return elastic;
  }

  protected ObjectMapper getMapper() {
    return mapper;
  }

  public NativeSearchQueryBuilder queryBuilder() {
    return new NativeSearchQueryBuilder()
        .withIndices(index)
        .withTypes(type);
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

  public Flux<E> search(final NativeSearchQuery query, final int offset, final int limit) {
    return getElastic().query(query, resultsToDocumentExtractor());
  }

  public Flux<TimeBin> timelineAggregation(final Optional<QueryBuilder> q, final String field) {
    final NativeSearchQueryBuilder qb = queryBuilder()
        .addAggregation(new DateHistogramAggregationBuilder("agg").field(field));

    if (q.isPresent()) {
      qb.withQuery(q.get());
    }

    return query(qb, response -> {
      final ParsedDateHistogram terms = response.getAggregations().get("agg");
      return Flux.fromIterable(terms.getBuckets()).map(b -> {
        final Instant i = Instant.ofEpochMilli(((DateTime) b.getKey()).toInstant().getMillis());
        return new TimeBin(i, b.getDocCount());
      });
    });
  }

  public <T> T query(final NativeSearchQueryBuilder qb, final ResultsExtractor<T> extractor) {
    return getElastic().query(qb.build(), extractor);
  }


  public Flux<TermBin> termAggregation(final Optional<QueryBuilder> q, final String field, final int size) {
    final NativeSearchQueryBuilder qb = queryBuilder()
        .addAggregation(new TermsAggregationBuilder("agg", ValueType.STRING).field(field).size(size));

    if (q.isPresent()) {
      qb.withQuery(q.get());
    }

    return getElastic().query(qb.build(), response -> {
      final StringTerms terms = response.getAggregations().get("agg");
      return Flux.fromIterable(terms.getBuckets())
          .map(b -> new TermBin(b.getKeyAsString(), b.getDocCount()));
    });
  }


  public Mono<Long> count() {
    return count(queryBuilder().withQuery(QueryBuilders.matchAllQuery()));
  }

  public Mono<Long> count(final NativeSearchQueryBuilder query) {
    return Mono
        .just(getElastic().count(query.build()));
  }

  public Mono<Long> count(final QueryBuilder query) {
    return count(queryBuilder().withQuery(query));
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

  protected ResultsExtractor<Flux<E>> resultsToDocumentExtractor() {
    return response -> SourceUtils.convertHits(getMapper(), response, entityClazz);
  }

}
