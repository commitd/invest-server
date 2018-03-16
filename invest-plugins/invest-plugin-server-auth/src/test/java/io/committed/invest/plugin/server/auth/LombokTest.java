package io.committed.invest.plugin.server.auth;

import org.junit.Test;
import io.committed.invest.plugin.server.auth.dao.UserAccount;
import io.committed.invest.plugin.server.auth.dto.User;
import io.committed.invest.test.LombokDataTestSupport;

public class LombokTest {

  @Test
  public void testLombok() {
    final LombokDataTestSupport mt = new LombokDataTestSupport();
    mt.testClass(UserAccount.class);
    mt.testClass(User.class);

  }
}
