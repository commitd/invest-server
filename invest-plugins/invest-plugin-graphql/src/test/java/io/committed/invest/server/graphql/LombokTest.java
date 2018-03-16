package io.committed.invest.server.graphql;

import org.junit.Test;
import io.committed.invest.server.graphql.data.GraphQlQuery;
import io.committed.invest.server.graphql.data.GraphQlServices;
import io.committed.invest.test.LombokDataTestSupport;

public class LombokTest {

  @Test
  public void testLombok() {
    final LombokDataTestSupport mt = new LombokDataTestSupport();
    mt.testClass(GraphQlQuery.class);
    mt.testClass(GraphQlServices.class);

  }
}
