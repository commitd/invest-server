package io.committed.vessel.plugin.server.auth.graphql;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.server.WebSession;

import io.committed.vessel.core.graphql.Context;
import io.committed.vessel.extensions.graphql.VesselGraphQlService;
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
    // if (user == null) {
    // return null;
    // }
    //
    // Set<String> roles;
    //
    // if (user instanceof Authentication) {
    // final Authentication auth = (Authentication) user;
    // roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
    // .collect(Collectors.toSet());
    // } else {
    // roles = Collections.emptySet();
    // }
    //
    // return new VesselUser(user.getName(), true, roles);


    try {
      // TODO: Remove blocks but for debugging its much easier
      final Authentication authentication = authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(username, password)).block();
      // NOTE: Must be exactly attribute as final WebSessionSecurityContextRepository
      final WebSession session = (WebSession) context.getSession().block();
      final SecurityContextImpl securityContext = new SecurityContextImpl();
      securityContext.setAuthentication(authentication);
      session.getAttributes().put("USER", securityContext);
      return Mono.just((User) authentication.getPrincipal());
    } catch (final Exception e) {
      log.warn("Authentication failed for {}", username, e);
      return Mono.empty();
    }
  }

  @GraphQLQuery(name = "session", description = "Get the user's session id")
  public Mono<String> userSession(@GraphQLContext final User user,
      @GraphQLRootContext final Context context) {
    return context.getSession()
        .map(s -> (WebSession) s)
        .map(WebSession::getId);
  }

  @GraphQLQuery(name = "user", description = "Get user details")
  public Mono<User> user(@GraphQLRootContext final Context context) {

    return context.getAuthentication().map(a -> {
      final Authentication auth = (Authentication) a;
      return (User) auth.getPrincipal();
    });


  }


  @GraphQLMutation(name = "logout", description = "Log out the current session")
  public Mono<Boolean> logout(@GraphQLRootContext final Context context) {
    return context.getSession().doOnNext(s -> {
      ((WebSession) s).getAttributes().remove("USER");
    }).then(Mono.just(true));
  }


  @GraphQLMutation(name = "changePassword")
  @PreAuthorize("isAuthenticated()")
  public void changePassword(
      @GraphQLArgument(name = "username") final String username,
      @GraphQLArgument(name = "password") final String password) {


    // TODO
    // if (securityService.hasAuthoritory(authentication, VesselRoles.ADMINISTRATOR_AUTHORITY)
    // || authentication.getName().equalsIgnoreCase(username)) {
    // securityService.changePassword(username, password);
    // } else {
    // log.warn("Attempt by user {} to change password for {}",
    // authentication.getName(), username);
    // }
  }
}
