package io.committed.invest.plugin.server.auth.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUtilsTest {

  @Test
  public void testFromAuth() {
    final Authentication auth = mock(Authentication.class);
    final UserDetails userDetails =
        new User("username", "password", Arrays.asList(new SimpleGrantedAuthority("ROLE_role")));
    doReturn(userDetails).when(auth).getPrincipal();
    final io.committed.invest.plugin.server.auth.dto.User user = AuthUtils.fromAuthentication(auth);

    assertThat(user.getName()).isEqualTo("username");
    assertThat(user.getUsername()).isEqualTo("username");
    assertThat(user.getRoles()).containsExactly("role");
  }

  @Test
  public void testRolesFromAuthorities() {
    final Set<String> rolesFromAuthorities =
        AuthUtils.getRolesFromAuthorities(
            Arrays.asList(
                new SimpleGrantedAuthority("ROLE_role1"),
                new SimpleGrantedAuthority("ROLE_role2"),
                new SimpleGrantedAuthority("role3")));

    assertThat(rolesFromAuthorities).containsExactly("role1", "role2");
  }

  @Test
  public void testToGrantAuthorities() {
    final Collection<GrantedAuthority> authorities =
        AuthUtils.toGrantAuthorities(new HashSet<>(Arrays.asList("role1", "role2")));
    assertThat(authorities.stream().map(GrantedAuthority::getAuthority))
        .containsExactly("role1", "role2");
  }

  @Test
  public void testHasAuthority() {
    final Authentication auth = mock(Authentication.class);
    final UserDetails userDetails =
        new User("username", "password", Arrays.asList(new SimpleGrantedAuthority("ROLE_role1")));
    doReturn(userDetails).when(auth).getPrincipal();
    doReturn(userDetails.getAuthorities()).when(auth).getAuthorities();

    assertThat(AuthUtils.hasAuthority(auth, "ROLE_role1")).isTrue();

    assertThat(AuthUtils.hasAuthority(auth, "ROLE_role10")).isFalse();
  }

  @Test
  public void testRolesToSet() {
    final String[] roles = new String[] {"a", "b", "c"};
    assertThat(AuthUtils.toSet(roles)).hasSameElementsAs(Arrays.asList(roles));
  }
}
