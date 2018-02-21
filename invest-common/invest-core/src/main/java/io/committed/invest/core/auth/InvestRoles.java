package io.committed.invest.core.auth;

public class InvestRoles {

  private InvestRoles() {
    // Singleton
  }

  public static final String ADMINISTRATOR = "ADMIN";
  public static final String USER = "USER";
  public static final String DEV = "DEV";


  // Convert to Authorities

  public static final String AUTHORITY_PREFIX = "ROLE_";

  public static final String ROLE_ADMINISTRATOR = AUTHORITY_PREFIX + ADMINISTRATOR;
  public static final String ROLE_USER = AUTHORITY_PREFIX + USER;
  public static final String ROLE_DEV = AUTHORITY_PREFIX + DEV;


}
