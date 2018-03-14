package io.committed.invest.plugin.server.audit.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * An audit service which pushed audit messages to the log.
 */
@Slf4j
public class Slf4jAuditService implements AuditService {

  private static final Logger AUDIT_LOG = LoggerFactory.getLogger("audit");

  private final ObjectMapper mapper;

  public Slf4jAuditService(final ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public void audit(final String user, final String action, final String message,
      final Object params) {
    String paramsString = "";
    if (params != null) {
      try {
        paramsString = mapper.writeValueAsString(params);
      } catch (final JsonProcessingException e) {
        paramsString = "Audit error";
        log.error("Unable to serialise audit log params", e);
      }
    }

    AUDIT_LOG.info("user:{};action:{};msg:{};params={}", user, action, message, paramsString);
  }

  @Override
  public boolean isLogging() {
    return true;
  }
}
