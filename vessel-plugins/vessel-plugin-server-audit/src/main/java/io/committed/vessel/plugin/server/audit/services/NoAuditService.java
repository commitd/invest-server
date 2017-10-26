package io.committed.vessel.plugin.server.audit.services;


public class NoAuditService implements AuditService {

  @Override
  public void audit(final String user, final String action, final String message,
      final Object params) {
    // Do nothing
  }

  @Override
  public boolean isLogging() {
    return false;
  }
}
