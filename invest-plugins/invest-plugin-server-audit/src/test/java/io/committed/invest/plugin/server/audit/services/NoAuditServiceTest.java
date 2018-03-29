package io.committed.invest.plugin.server.audit.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;

import org.junit.Test;

public class NoAuditServiceTest {

  @Test
  public void testAudit() {
    final NoAuditService service = new NoAuditService();

    service.audit(null, null, null, null);
    service.audit("test", "msg", "action", new HashMap<>());
  }

  @Test
  public void testIsLogging() {
    final NoAuditService service = new NoAuditService();
    assertThat(service.isLogging()).isFalse();
  }
}
