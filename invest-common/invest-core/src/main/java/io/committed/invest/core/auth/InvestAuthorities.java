package io.committed.invest.core.auth;

/**
 * Invest Security Authorities for Spring
 */
public class InvestAuthorities {

  // Convert to Authorities

  public static final String ADMINISTRATOR = "ADMIN";
  public static final String USER = "USER";
  public static final String DEV = "DEV";

  private InvestAuthorities() {
    // Singleton
  }

}
