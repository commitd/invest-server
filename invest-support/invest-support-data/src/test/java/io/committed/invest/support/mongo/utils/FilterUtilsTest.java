package io.committed.invest.support.mongo.utils;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import org.bson.conversions.Bson;
import org.junit.Test;
import com.mongodb.client.model.Filters;

public class FilterUtilsTest {

  @Test
  public void testNull() {
    assertThat(FilterUtils.combine(null)).isEmpty();
  }

  @Test
  public void testEmpty() {
    assertThat(FilterUtils.combine(Collections.emptyList())).isEmpty();
  }


  @Test
  public void testSingleton() {
    final Bson eq = Filters.eq("a", "b");
    final Optional<Bson> combine = FilterUtils.combine(Collections.singletonList(eq));
    assertThat(combine.get()).isSameAs(eq);
  }

  @Test
  public void testMulti() {
    final Bson a = Filters.eq("a", "1");
    final Bson b = Filters.eq("b", "2");

    final Optional<Bson> combine = FilterUtils.combine(Arrays.asList(a, b));

    // Private class... so just verify what it should be... weak
    final String string = combine.get().toString();

    assertThat(string).contains("And Filter");
    assertThat(string).contains("fieldName='a', value=1");
    assertThat(string).contains("fieldName='b', value=2");


  }
}
