package io.committed.invest.plugin.server.auth.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import io.committed.invest.plugin.server.auth.constants.InvestRoles;
import io.committed.invest.plugin.server.auth.dto.User;

public final class AuthUtils {

  private AuthUtils() {
    // Singleton
  }

  public static User fromAuthentication(final Authentication auth) {
    final UserDetails ud = (UserDetails) auth.getPrincipal();
    return new User(ud.getUsername(), ud.getName(), getRolesFromAuthorities(ud.getAuthorities()));
  }

  public static Set<String> getRolesFromAuthorities(
      final Collection<? extends GrantedAuthority> authorities) {
    return authorities == null ? Collections.emptySet()
        : authorities.stream().map(GrantedAuthority::getAuthority)
            .filter(a -> a.startsWith(InvestRoles.AUTHORITY_PREFIX))
            .map(a -> a.substring(InvestRoles.AUTHORITY_PREFIX.length()))
            .collect(Collectors.toSet());
  }

  public static Collection<GrantedAuthority> toGrantAuthorities(final Set<String> authorities) {
    return authorities.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}
