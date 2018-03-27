package io.committed.invest.core.auth;

import java.util.Optional;

/**
 * Invest Security Roles for Spring and UI
 */
public class InvestRoles {

  private InvestRoles() {
    // Singleton
  }

  private static final String AUTHORITY_PREFIX = "ROLE_";

  public static final String ROLE_ADMINISTRATOR = fromRoleToAuthority(InvestAuthorities.ADMINISTRATOR);
  public static final String ROLE_USER = fromRoleToAuthority(InvestAuthorities.USER);
  public static final String ROLE_DEV = fromRoleToAuthority(InvestAuthorities.DEV);


  public static boolean isAuthorityARole(final String authority) {
    return authority.startsWith(AUTHORITY_PREFIX);
  }

  public static Optional<String> fromAuthorityToRole(final String authority) {
    if (isAuthorityARole(authority)) {
      return Optional.of(authority.substring(AUTHORITY_PREFIX.length()));
    } else {
      return Optional.empty();
    }
  }

  public static String fromRoleToAuthority(final String role) {
    return AUTHORITY_PREFIX + role;
  }
}
