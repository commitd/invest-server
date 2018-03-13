package io.committed.invest.core.auth;

/**
 * Invest Security Roles for Spring and UI
 */
public class InvestRoles {

  private InvestRoles() {
    // Singleton
  }

  public static final String AUTHORITY_PREFIX = "ROLE_";

  public static final String ROLE_ADMINISTRATOR = AUTHORITY_PREFIX + InvestAuthorities.ADMINISTRATOR;
  public static final String ROLE_USER = AUTHORITY_PREFIX + InvestAuthorities.USER;
  public static final String ROLE_DEV = AUTHORITY_PREFIX + InvestAuthorities.DEV;

}
