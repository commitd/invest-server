package io.committed.invest.support.elasticsearch.utils;

import java.io.IOException;
import java.util.Optional;
import org.elasticsearch.common.geo.builders.CoordinatesBuilder;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.common.geo.builders.ShapeBuilders;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import io.committed.invest.core.dto.analytic.GeoBox;
import io.committed.invest.core.dto.analytic.GeoRadius;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class GeoUtils {

  private GeoUtils() {
    // Singleton
  }

  public static Optional<QueryBuilder> createGeoShapeQuery(final String field, final GeoRadius gr) {
    final ShapeBuilder shape = ShapeBuilders.newCircleBuilder()
        .center(gr.getLon(), gr.getLat())
        .radius(gr.getRadius(), DistanceUnit.METERS);

    return createGeoShapeQuery(field, shape);
  }

  public static Optional<QueryBuilder> createGeoShapeQuery(final String field, final GeoBox box) {
    final CoordinatesBuilder shell = new CoordinatesBuilder()
        .coordinate(box.getSafeW(), box.getSafeS())
        .coordinate(box.getSafeE(), box.getSafeN())
        .coordinate(box.getSafeE(), box.getSafeN())
        .coordinate(box.getSafeW(), box.getSafeS())
        .close();
    final ShapeBuilder shape = ShapeBuilders.newPolygon(shell);

    return createGeoShapeQuery(field, shape);
  }

  public static Optional<QueryBuilder> createGeoShapeQuery(final String field,
      final ShapeBuilder shape) {
    try {
      return Optional.of(
          QueryBuilders.geoShapeQuery(field, shape));
    } catch (final IOException e) {
      log.warn("Unable to create query by geometry", e.getMessage());
      log.debug("Full exception was ", e);
      return Optional.empty();
    }
  }
}
