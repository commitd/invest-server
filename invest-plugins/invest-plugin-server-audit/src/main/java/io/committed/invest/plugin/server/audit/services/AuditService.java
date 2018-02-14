package io.committed.invest.plugin.server.audit.services;


public interface AuditService {

  void audit(String user, String action, String message, Object params);

  boolean isLogging();
}
