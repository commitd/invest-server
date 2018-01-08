package io.committed.invest.support.data.elasticsearch;

import java.time.Instant;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
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

public abstract class AbstractEsService<E> {

  private final Client client;
  private final ObjectMapper mapper;
  private final ElasticsearchTemplate elastic;
  private final String index;
  private final String type;
  private final Class<E> entityClazz;

  public AbstractEsService(final ObjectMapper mapper, final ElasticsearchTemplate elastic,
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

  protected NativeSearchQueryBuilder queryBuilder() {
    return new NativeSearchQueryBuilder().withIndices(index).withTypes(type);
  }

  protected Flux<TimeBin> timelineAggregation(final String field) {
    final NativeSearchQuery query = queryBuilder()
        .addAggregation(new DateHistogramAggregationBuilder("agg").field(field)).build();

    return getElastic().query(query, response -> {
      final ParsedDateHistogram terms = response.getAggregations().get("agg");
      return Flux.fromIterable(terms.getBuckets()).map(b -> {
        final Instant i = Instant.ofEpochMilli(((DateTime) b.getKey()).toInstant().getMillis());
        return new TimeBin(i, b.getDocCount());
      });
    });
  }

  protected Flux<TermBin> termAggregation(final String field) {
    final NativeSearchQuery query = queryBuilder()
        .addAggregation(new TermsAggregationBuilder("agg", ValueType.STRING).field(field)).build();

    return getElastic().query(query, response -> {
      final StringTerms terms = response.getAggregations().get("agg");
      return Flux.fromIterable(terms.getBuckets())
          .map(b -> new TermBin(b.getKeyAsString(), b.getDocCount()));
    });
  }

  protected Mono<E> getDocumentById(final String id) {
    final ListenableActionFuture<GetResponse> future =
        getClient().prepareGet().setIndex(index).setType(type).setId(id).execute();

    return ReactiveElasticsearchUtils.toMono(future)
        .flatMap(r -> SourceUtils.convertSource(getMapper(), r.getSourceAsString(), entityClazz));
  }

  protected ResultsExtractor<Flux<E>> resultsToDocumentExtractor() {
    return response -> SourceUtils.convertHits(getMapper(), response, entityClazz);
  }


  protected Flux<E> searchForDocuments(final String search, final int offset, final int limit) {
    final ListenableActionFuture<SearchResponse> future = getClient().prepareSearch()
        .setIndices(index).setTypes(type).setQuery(QueryBuilders.queryStringQuery(search))
        .setFrom(offset).setSize(limit).execute();

    return ReactiveElasticsearchUtils.toMono(future)
        .flatMapMany(r -> SourceUtils.convertHits(getMapper(), r, entityClazz));
  }
}
