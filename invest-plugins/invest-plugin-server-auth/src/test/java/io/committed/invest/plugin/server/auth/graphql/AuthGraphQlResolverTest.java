package io.committed.invest.plugin.server.auth.graphql;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.WebSession;

import com.google.common.collect.ImmutableSet;

import io.committed.invest.core.auth.InvestRoles;
import io.committed.invest.core.graphql.InvestRootContext;
import io.committed.invest.plugin.server.auth.config.MemAuthConfig;
import io.committed.invest.plugin.server.auth.dto.User;
import io.committed.invest.plugin.server.services.UserService;
import reactor.core.publisher.Mono;

@SuppressWarnings("deprecation")
public class AuthGraphQlResolverTest {

  private AuthGraphQlResolver resolver;
  private InvestRootContext investRootContext;
  private AbstractAuthenticationToken auth;

  @Before
  public void setup() {
    PasswordEncoder pE = NoOpPasswordEncoder.getInstance();
    resolver = new AuthGraphQlResolver(null, new UserService(new MemAuthConfig().userDetailsRepository(pE), pE));
    final Mono<WebSession> session = Mono.just(mock(WebSession.class));
    auth = mock(AbstractAuthenticationToken.class);
    investRootContext = new InvestRootContext(session, Mono.just(auth));
  }

  @Test
  public void testAdminDeleteExistingUser() {
    withRoles(InvestRoles.ROLE_ADMINISTRATOR);

    String username = "user";
    resolver.deleteUser(investRootContext, username);
    List<User> after = resolver.allUsers(investRootContext).collect(toList()).block();
    assertTrue(after.stream().noneMatch(u -> u.getUsername().equals(username)));
  }

  @Test
  public void testNonAdminCantDeleteUser() {
    withRoles(InvestRoles.ROLE_USER);

    String username = "user";
    resolver.deleteUser(investRootContext, username);
    List<User> after = resolver.allUsers(investRootContext).collect(toList()).block();
    assertTrue(after.stream().anyMatch(u -> u.getUsername().equals(username)));
  }

  @Test
  public void testAdminDeleteNonExistingUser() {
    withRoles(InvestRoles.ROLE_ADMINISTRATOR);

    List<User> before = resolver.allUsers(investRootContext).collect(toList()).block();
    String username = "non-existing-user";
    resolver.deleteUser(investRootContext, username);
    List<User> after = resolver.allUsers(investRootContext).collect(toList()).block();
    assertEquals(before.size(), after.size());
  }

  @Test
  public void testAdminAddNewUser() {
    withRoles(InvestRoles.ROLE_ADMINISTRATOR);

    String username = "newUser";
    resolver.saveUser(investRootContext, new User(username, username, ImmutableSet.of(InvestRoles.ROLE_USER)), "123");

    List<User> after = resolver.allUsers(investRootContext).collect(toList()).block();
    assertTrue(after.stream().anyMatch(u -> u.getUsername().equals(username)));
  }

  @Test
  public void testNonAdminCantAddNewUser() {
    withRoles(InvestRoles.ROLE_USER);

    String username = "newUser";
    resolver.saveUser(investRootContext, new User(username, username, ImmutableSet.of(InvestRoles.ROLE_USER)), "123");

    List<User> after = resolver.allUsers(investRootContext).collect(toList()).block();
    assertTrue(after.stream().noneMatch(u -> u.getUsername().equals(username)));
  }

  @Test
  public void testAdminEditExistingUser() {
    withRoles(InvestRoles.ROLE_ADMINISTRATOR);

    String username = "user";
    String newRole = InvestRoles.ROLE_DEV;
    resolver.saveUser(investRootContext, new User(username, username, ImmutableSet.of(newRole)), "123").block();
    List<User> after = resolver.allUsers(investRootContext).collect(toList()).block();
    Optional<User> findFirst = after.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    assertTrue(findFirst.isPresent());
    // why has the dev role become an authority? Probable bug
    assertTrue(findFirst.get().getRoles().contains(InvestRoles.fromAuthorityToRole(newRole).get()));
  }

  private void withRoles(String... roles) {
    Collection<GrantedAuthority> authoritites = Stream.of(roles).map(SimpleGrantedAuthority::new).collect(toList());
    when(auth.getAuthorities()).thenReturn(authoritites);
  }

}
