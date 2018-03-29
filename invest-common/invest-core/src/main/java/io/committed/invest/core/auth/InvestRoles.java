package io.committed.invest.core.auth;

import java.util.Optional;

/** Invest Security Roles for Spring and UI */
public class InvestRoles {

  private InvestRoles() {
    // Singleton
  }

  private static final String AUTHORITY_PREFIX = "ROLE_";

  public static final String ADMINISTRATOR = "ADMIN";
  public static final String USER = "USER";
  public static final String DEV = "DEV";

  // public static final String ROLE_ADMINISTRATOR =
  // fromRoleToAuthority(InvestAuthorities.ADMINISTRATOR);
  // public static final String ROLE_USER = fromRoleToAuthority(InvestAuthorities.USER);
  // public static final String ROLE_DEV = fromRoleToAuthority(InvestAuthorities.DEV);

  public static final String ADMINISTRATOR_AUTHORITY = fromRoleToAuthority(ADMINISTRATOR);
  public static final String USER_AUTHORITY = fromRoleToAuthority(USER);
  public static final String DEV_AUTHORITY = fromRoleToAuthority(DEV);

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
