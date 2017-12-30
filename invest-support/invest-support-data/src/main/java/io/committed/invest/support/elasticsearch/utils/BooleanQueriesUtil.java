package io.committed.invest.support.elasticsearch.utils;

import java.util.Arrays;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public final class BooleanQueriesUtil {

  public static QueryBuilder andQueries(final List<QueryBuilder> queries) {
    if (queries.size() == 1) {
      return queries.get(0);
    }
    BoolQueryBuilder andQuery = QueryBuilders.boolQuery();
    for (final QueryBuilder qb : queries) {
      if (qb != null) {
        andQuery = andQuery.must(qb);
      }
    }
    return andQuery;
  }

  public static QueryBuilder andQueries(final QueryBuilder... queries) {
    return andQueries(Arrays.asList(queries));
  }

  public static QueryBuilder notQuery(final QueryBuilder query) {
    final BoolQueryBuilder notQuery = QueryBuilders.boolQuery();
    return notQuery.mustNot(query);
  }

  public static QueryBuilder orQueries(final List<QueryBuilder> queries) {
    if (queries.size() == 1) {
      return queries.get(0);
    }
    BoolQueryBuilder andQuery = QueryBuilders.boolQuery();
    for (final QueryBuilder qb : queries) {
      if (qb != null) {
        andQuery = andQuery.should(qb);
      }
    }
    return andQuery;
  }

  public static QueryBuilder orQueries(final QueryBuilder... queries) {
    return orQueries(Arrays.asList(queries));
  }

  private BooleanQueriesUtil() {
    // Singleton
  }

}
