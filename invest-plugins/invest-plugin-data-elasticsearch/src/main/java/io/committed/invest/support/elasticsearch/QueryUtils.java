package io.committed.invest.support.elasticsearch;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoBoundingBoxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;

import io.committed.invest.core.dto.analytic.GeoBox;
import io.committed.invest.core.dto.analytic.GeoRadius;
import io.committed.invest.core.dto.analytic.TimeRange;

public class QueryUtils {
  public QueryBuilder createGetByIdQuery(final String type, final String id) {
    return BooleanQueriesUtil.andQueries(QueryBuilders.termQuery("_id", id),
        QueryBuilders.termQuery("type", type));
  }

  public QueryBuilder createOrTermQuery(final String field, final Collection<String> terms) {
    final List<QueryBuilder> typeQueries =
        terms.stream().map(s -> QueryBuilders.termQuery(field, s)).collect(Collectors.toList());
    return BooleanQueriesUtil.orQueries(typeQueries);
  }



  public GeoBoundingBoxQueryBuilder createBoundingBoxQuery(final String field, final GeoBox box) {
    return QueryBuilders.geoBoundingBoxQuery(field)
        .setCorners(box.getSafeN(), box.getSafeW(), box.getSafeS(), box.getSafeE())
        .type("indexed");
  }

  public QueryStringQueryBuilder createQueryString(final String defaultField, final String query) {
    return QueryBuilders.queryStringQuery(query).defaultField(defaultField);
  }

  public QueryBuilder createRadiusQuery(final String field, final GeoRadius geoRadius) {
    return QueryBuilders.geoDistanceQuery(field).point(geoRadius.getLat(), geoRadius.getLon())
        .distance(geoRadius.getRadius(), DistanceUnit.METERS);
  }

  public QueryBuilder createTimeRangeQuery(final String field, final TimeRange range) {
    RangeQueryBuilder query = QueryBuilders.rangeQuery(field);
    if (range != null) {
      if (range.getStart() != null) {
        query = query.gte(range.getStart());
      }
      if (range.getEnd() != null) {
        query = query.lte(range.getEnd());
      }
    }
    return query;
  }
}
