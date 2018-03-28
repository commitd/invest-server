package io.committed.invest.plugin.server.audit.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Slf4jAuditServiceTest {

  @Test
  public void testAudit() {
    final Slf4jAuditService service = new Slf4jAuditService(new ObjectMapper());

    service.audit(null, null, null, null);
    service.audit("test", "msg", "action", new HashMap<>());
  }

  @Test
  public void testIsLogging() {
    final Slf4jAuditService service = new Slf4jAuditService(new ObjectMapper());
    assertThat(service.isLogging()).isTrue();
  }
}
