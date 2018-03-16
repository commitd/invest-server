package io.committed.invest.extensions;

import org.junit.Test;
import io.committed.invest.extensions.actions.SimpleActionDefinition;
import io.committed.invest.extensions.collections.InvestExtensions;
import io.committed.invest.extensions.data.dataset.DataProviderSpecification;
import io.committed.invest.extensions.data.dataset.Dataset;
import io.committed.invest.extensions.data.query.DataHints;
import io.committed.invest.test.LombokDataTestSupport;

public class LombokTests {

  @Test
  public void testLombok() {
    final LombokDataTestSupport mt = new LombokDataTestSupport();
    mt.testClass(SimpleActionDefinition.class);

    mt.testClass(InvestExtensions.class);

    mt.testClasses(Dataset.class, DataProviderSpecification.class);

    mt.testClass(DataHints.class);

  }
}
