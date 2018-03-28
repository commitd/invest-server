package io.committed.invest.plugin.server.audit.services;

/** An audit implementation which does nothing. */
public class NoAuditService implements AuditService {

  @Override
  public void audit(
      final String user, final String action, final String message, final Object params) {
    // Do nothing
  }

  @Override
  public boolean isLogging() {
    return false;
  }
}
