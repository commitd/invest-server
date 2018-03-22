package io.committed.invest.support.data;

import org.junit.Test;
import io.committed.invest.support.data.elasticsearch.SearchHits;
import io.committed.invest.test.LombokDataTestSupport;

public class LombokTest {

  @Test
  public void testLombok() {
    final LombokDataTestSupport mt = new LombokDataTestSupport();

    mt.testClass(SearchHits.class);
  }

}
