package io.committed.invest.plugin.server.auth.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import io.committed.invest.core.auth.InvestRoles;
import io.committed.invest.plugin.server.auth.dao.UserAccount;
import io.committed.invest.plugin.server.auth.dto.User;

/**
 * Utilities for authentication
 *
 */
public final class AuthUtils {

  private AuthUtils() {
    // Singleton
  }

  /**
   * Convert from a Spring Authentication to a User dto.
   *
   * @param auth the auth
   * @return the user
   */
  public static User fromAuthentication(final Authentication auth) {
    final UserDetails ud = (UserDetails) auth.getPrincipal();
    return new User(ud.getUsername(), ud.getUsername(), getRolesFromAuthorities(ud.getAuthorities()));
  }

  /**
   * Convert from a User Account dao to a User dto.
   *
   * @param auth the auth
   * @return the user
   */
  public static User fromAccount(final UserAccount userAccount) {
    return new User(userAccount.getUsername(), userAccount.getUsername(),
        getRolesFromAuthorities(userAccount.getAuthorities().stream()));
  }

  /**
   * Convert roles to authoritories.
   *
   * @param authorities the authorities
   * @return the roles from authorities
   */
  public static Set<String> getRolesFromAuthorities(final Collection<? extends GrantedAuthority> authorities) {
    return authorities == null ? Collections.emptySet()
        : getRolesFromAuthorities(authorities.stream().map(GrantedAuthority::getAuthority));
  }

  private static Set<String> getRolesFromAuthorities(final Stream<String> authorities) {
    return authorities.filter(InvestRoles::isAuthorityARole).map(InvestRoles::fromAuthorityToRole)
        .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
  }

  /**
   * Convert from string authorities to Spring GrantedAuthorities.
   *
   * @param authorities the authorities
   * @return the collection
   */
  public static Collection<GrantedAuthority> toGrantAuthorities(final Set<String> authorities) {
    return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  public static boolean hasAuthority(final Authentication authentication, final String... authority) {
    final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    if (authorities == null || authorities.isEmpty()) {
      return false;
    }

    return Arrays.stream(authority)
        .anyMatch(auth -> authorities.stream().anyMatch(a -> a.getAuthority().equals(auth)));
  }

  public static Set<String> toSet(final String... roles) {
    return new HashSet<>(Arrays.asList(roles));
  }
}
