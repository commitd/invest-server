package io.committed.invest.core;

import org.junit.Test;
import io.committed.invest.core.auth.AuthenticationSettings;
import io.committed.invest.core.dto.analytic.GeoBin;
import io.committed.invest.core.dto.auth.InvestUser;
import io.committed.invest.core.dto.collections.Property;
import io.committed.invest.core.dto.graph.DefaultEdge;
import io.committed.invest.core.dto.system.Message;
import io.committed.invest.core.graphql.InvestRootContext;
import io.committed.invest.test.LombokDataTestSupport;

public class LombokTest {

  @Test
  public void testLombok() {
    final LombokDataTestSupport mt = new LombokDataTestSupport();
    mt.testPackage(GeoBin.class);
    mt.testPackage(InvestUser.class);
    mt.testPackage(Property.class);
    mt.testClass(DefaultEdge.class);
    mt.testPackage(Message.class);
    mt.testPackage(InvestRootContext.class);
    mt.testPackage(AuthenticationSettings.class);
  }
}
