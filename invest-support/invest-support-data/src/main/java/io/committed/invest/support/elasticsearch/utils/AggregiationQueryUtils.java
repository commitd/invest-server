package io.committed.invest.support.elasticsearch.utils;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.geogrid.GeoGridAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.geogrid.GeoHashGrid;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;

import io.committed.invest.core.dto.constants.TimeInterval;

public class AggregiationQueryUtils {

  public static final int MIN_GEOHASH_PRECISION = 1;
  public static final int MAX_GEOHASH_PRECISION = 20;


  public Map<String, Long> convertGeohashAggregationToMap(final SearchResponse r,
      final String aggregationName) {
    final GeoHashGrid agg = r.getAggregations().get(aggregationName);
    return agg.getBuckets().stream().collect(
        Collectors.toMap(GeoHashGrid.Bucket::getKeyAsString, GeoHashGrid.Bucket::getDocCount));
  }

  public Map<String, Long> convertTermsAggregationToMap(final SearchResponse r,
      final String aggregationName) {
    final Terms terms = r.getAggregations().get(aggregationName);
    return terms.getBuckets().stream()
        .collect(Collectors.toMap(Terms.Bucket::getKeyAsString, Terms.Bucket::getDocCount));
  }

  public Map<String, Long> convertTimelineAggregationToMap(final SearchResponse r,
      final String aggregationName) {
    final Histogram agg = r.getAggregations().get(aggregationName);
    return agg.getBuckets().stream()
        .sorted((b1, b2) -> b1.getKeyAsString().compareTo(b2.getKeyAsString()))
        .collect(Collectors.toMap(Histogram.Bucket::getKeyAsString, Histogram.Bucket::getDocCount,
            (u, v) -> {
              throw new IllegalStateException(String.format("Duplicate key %s", u));
            }, TreeMap::new));
  }

  public GeoGridAggregationBuilder createGeohashAggregation(final String field, final int precision,
      final int size) {
    final GeoGridAggregationBuilder builder = AggregationBuilders.geohashGrid(field).field(field)
        .precision(Math.max(Math.min(precision, MAX_GEOHASH_PRECISION), MIN_GEOHASH_PRECISION));
    builder.size(size > 0 ? size : 0);
    return builder;
  }


  public TermsAggregationBuilder createTermAggregation(final String field, final int size) {
    final TermsAggregationBuilder builder = AggregationBuilders.terms(field).field(field);
    builder.size(size > 0 ? size : 0);
    return builder;
  }

  public DateHistogramAggregationBuilder createTimelineAggregation(final String field,
      final TimeInterval interval) {
    return AggregationBuilders.dateHistogram(field).field(field)
        .dateHistogramInterval(TimeIntervalUtils.toDateHistogram(interval));
  }

}
