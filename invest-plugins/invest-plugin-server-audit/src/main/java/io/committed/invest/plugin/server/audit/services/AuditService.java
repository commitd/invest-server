package io.committed.invest.plugin.server.audit.services;


/**
 * An interface which all audit services must implement.
 */
public interface AuditService {

  /**
   * Log the audit message.
   *
   * @param user the user (may be null if non-auth)
   * @param action an action represneting the action which is being perform
   * @param message the descriptive message (or null)
   * @param params the params any parameters (typically variables passed, or response codes)
   */
  void audit(String user, String action, String message, Object params);


  /**
   * Checks if is audit service is logging.
   *
   * If not then audit() will not be called (and other work will not be done).
   *
   * Most implementation will have this fixed (to true), but it is called each time an audit message
   * may be generated, so it can change dynamically.
   *
   * @return true, if is logging
   */
  boolean isLogging();
}
