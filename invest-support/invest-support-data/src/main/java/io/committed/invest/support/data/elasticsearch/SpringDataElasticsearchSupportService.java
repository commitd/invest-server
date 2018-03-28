package io.committed.invest.support.data.elasticsearch;

import java.util.Map;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.committed.invest.support.elasticsearch.utils.SourceUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * An extension of {@link ElasticsearchSupportService} which add helpers for Spring Data
 * Elasticsearch.
 *
 * This largely exists to support wrapping Spring Data {@link ElasticsearchSupportService} with
 * reactive types. Though it also reduces some boilerplate around index/type specification when
 * using {@link NativeSearchQueryBuilder}.
 *
 * @param <E> the element type backing the Spring Data {@link ElasticsearchSupportService}
 */
public class SpringDataElasticsearchSupportService<E> extends ElasticsearchSupportService<E> {

  private final ElasticsearchTemplate elastic;

  public SpringDataElasticsearchSupportService(final ObjectMapper mapper, final ElasticsearchTemplate elastic,
      final String index, final String type, final Class<E> entityClazz) {
    super(elastic.getClient(), mapper, index, type, entityClazz);
    this.elastic = elastic;
  }

  protected ElasticsearchTemplate getElastic() {
    return elastic;
  }

  public NativeSearchQueryBuilder queryBuilder() {
    return new NativeSearchQueryBuilder()
        .withIndices(getIndex())
        .withTypes(getType());
  }

  public <T> T query(final NativeSearchQueryBuilder qb, final ResultsExtractor<T> extractor) {
    return getElastic().query(qb.build(), extractor);
  }

  public Mono<Long> count(final NativeSearchQueryBuilder query) {
    return Mono
        .just(getElastic().count(query.build()));
  }

  protected ResultsExtractor<Flux<E>> resultsToDocumentExtractor() {
    return response -> SourceUtils.convertHits(getMapper(), response, getEntityClass());
  }

  @SuppressWarnings("unchecked")
  public Map<String, Object> getMapping() {
    return getElastic().getMapping(getIndex(), getType());
  }
}
