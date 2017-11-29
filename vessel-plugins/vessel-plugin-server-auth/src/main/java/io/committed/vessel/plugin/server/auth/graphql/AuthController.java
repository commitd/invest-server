package io.committed.vessel.plugin.server.auth.graphql;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.WebSession;

import io.committed.vessel.core.graphql.Context;
import io.committed.vessel.extensions.graphql.VesselGraphQlService;
import io.committed.vessel.plugin.server.auth.constants.VesselRoles;
import io.committed.vessel.plugin.server.auth.dto.User;
import io.committed.vessel.plugin.server.services.UserService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLRootContext;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@VesselGraphQlService
@Slf4j
public class AuthController {


  // THought we could use Flux and non-blocking code here, it feels more sensible to be
  // confident that everything has full executed in inside the functions.

  private final UserService securityService;
  private final ReactiveAuthenticationManager authenticationManager;

  public AuthController(final ReactiveAuthenticationManager authenticationManager,
      final UserService securityService) {
    this.authenticationManager = authenticationManager;
    this.securityService = securityService;
  }

  @GraphQLMutation(name = "login", description = "Perform user log in")
  public Mono<User> login(@GraphQLRootContext final Context context,
      @GraphQLNonNull @GraphQLArgument(name = "username") final String username,
      @GraphQLNonNull @GraphQLArgument(name = "password") final String password) {

    try {
      final Authentication authentication = authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(username, password)).block();
      final WebSession session = context.getSession().block();
      final SecurityContextImpl securityContext = new SecurityContextImpl();
      securityContext.setAuthentication(authentication);
      // NOTE: Must be exactly attribute as final WebSessionSecurityContextRepository
      session.getAttributes().put("USER", securityContext);
      return Mono.just(fromAuthentication(authentication));
    } catch (final Exception e) {
      log.warn("Authentication failed for {}", username, e);
      return Mono.empty();
    }
  }

  @GraphQLQuery(name = "session", description = "Get the user's session id")
  public Mono<String> userSession(@GraphQLContext final User user,
      @GraphQLRootContext final Context context) {
    return context.getSession()
        .map(WebSession::getId);
  }

  @GraphQLQuery(name = "user", description = "Get user details")
  public Mono<User> user(@GraphQLRootContext final Context context) {

    return context.getAuthentication().map(a -> {
      return fromAuthentication(a);
    });


  }


  @GraphQLMutation(name = "logout", description = "Log out the current session")
  public Mono<Boolean> logout(@GraphQLRootContext final Context context) {
    return context.getSession().doOnNext(s -> {
      s.getAttributes().remove("USER");
      s.invalidate();
    }).then(Mono.just(true));
  }


  @GraphQLMutation(name = "changePassword")
  public void changePassword(
      @GraphQLRootContext final Context context,
      @GraphQLArgument(name = "username") final String username,
      @GraphQLArgument(name = "password") final String password) {

    final Authentication authentication = context.getAuthentication().block();

    if (securityService.hasAuthority(authentication, VesselRoles.ROLE_ADMINISTRATOR)
        || authentication.getName().equals(username)) {
      securityService.changePassword(username, password);
    } else {
      log.warn("Attempt by user {} to change password for {}",
          authentication.getName(), username);
    }
  }

  // TODO: These could be Utils

  private User fromAuthentication(final Authentication auth) {
    final UserDetails ud = (UserDetails) auth.getPrincipal();
    return new User(ud.getUsername(), ud.getName(), getRolesFromAuthorities(ud.getAuthorities()));
  }

  private Set<String> getRolesFromAuthorities(
      final Collection<? extends GrantedAuthority> authorities) {
    return authorities == null ? Collections.emptySet()
        : authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .filter(a -> a.startsWith(VesselRoles.AUTHORITY_PREFIX))
            .map(a -> a.substring(VesselRoles.AUTHORITY_PREFIX.length()))
            .collect(Collectors.toSet());
  }
}
