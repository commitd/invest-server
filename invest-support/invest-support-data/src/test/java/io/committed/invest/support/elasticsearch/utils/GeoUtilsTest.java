package io.committed.invest.support.elasticsearch.utils;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import org.elasticsearch.common.geo.builders.ShapeBuilders;
import org.elasticsearch.index.query.GeoShapeQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Test;
import io.committed.invest.core.dto.analytic.GeoBox;
import io.committed.invest.core.dto.analytic.GeoRadius;

public class GeoUtilsTest {

  private static final String FIELD_NAME = "test.field";


  @Test
  public void testFromGeoRadius() {
    final Optional<QueryBuilder> optional =
        GeoUtils.createGeoShapeQuery(FIELD_NAME, new GeoRadius(-10.0, -30.0, 200.0));

    final GeoShapeQueryBuilder gsqb = (GeoShapeQueryBuilder) optional.get();

    final String string = gsqb.toString();

    // Poor but really just testing ES
    assertThat(string).contains("circle");
    assertThat(string).contains("-10.0");
    assertThat(string).contains("-30.0");
    assertThat(string).contains("200");

  }


  @Test
  public void testFromGeoBox() {
    final Optional<QueryBuilder> optional =
        GeoUtils.createGeoShapeQuery(FIELD_NAME, new GeoBox(-10.0, 30.0, 20.0, -3.0));

    final GeoShapeQueryBuilder gsqb = (GeoShapeQueryBuilder) optional.get();

    final String string = gsqb.toString();

    // Poor but really just testing ES
    assertThat(string).contains("polygon");
    assertThat(string).contains("-10.0");
    assertThat(string).contains("30.0");
  }


  @Test
  public void testFromShape() {
    final Optional<QueryBuilder> optional =
        GeoUtils.createGeoShapeQuery(FIELD_NAME, ShapeBuilders.newPoint(-2, -3));

    final GeoShapeQueryBuilder gsqb = (GeoShapeQueryBuilder) optional.get();
    final String string = gsqb.toString();
    assertThat(string).contains("point");

  }

}
