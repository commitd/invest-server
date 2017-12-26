package io.committed.invest.plugin.server.auth.constants;

public class InvestRoles {

  private InvestRoles() {
    // Singleton
  }

  public static final String ADMINISTRATOR = "ADMIN";
  public static final String USER = "USER";


  // Convert to Authorities

  public static final String AUTHORITY_PREFIX = "ROLE_";

  public static final String ROLE_ADMINISTRATOR =
      AUTHORITY_PREFIX + ADMINISTRATOR;
  public static final String ROLE_USER = AUTHORITY_PREFIX + USER;


}
