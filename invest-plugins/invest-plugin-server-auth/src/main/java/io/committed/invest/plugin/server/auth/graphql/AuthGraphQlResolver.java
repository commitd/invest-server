package io.committed.invest.plugin.server.auth.graphql;

import java.util.Optional;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.server.WebSession;

import io.committed.invest.core.auth.InvestRoles;
import io.committed.invest.core.graphql.InvestRootContext;
import io.committed.invest.extensions.annotations.GraphQLService;
import io.committed.invest.plugin.server.auth.dao.UserAccount;
import io.committed.invest.plugin.server.auth.dto.User;
import io.committed.invest.plugin.server.auth.utils.AuthUtils;
import io.committed.invest.plugin.server.services.UserService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLRootContext;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * GraphQL mutations and queries for authentication actions.
 *
 * Though we could use Flux and non-blocking code here, it feels more sensible to be confident that
 * everything has full executed in inside the functions.
 *
 */
@GraphQLService
@Slf4j
public class AuthGraphQlResolver {

  private final UserService securityService;
  private final ReactiveAuthenticationManager authenticationManager;

  public AuthGraphQlResolver(final ReactiveAuthenticationManager authenticationManager,
      final UserService securityService) {
    this.authenticationManager = authenticationManager;
    this.securityService = securityService;
  }

  @GraphQLMutation(name = "login", description = "Perform user log in")
  public Mono<User> login(@GraphQLRootContext final InvestRootContext context,
      @GraphQLNonNull @GraphQLArgument(name = "username") final String username,
      @GraphQLNonNull @GraphQLArgument(name = "password") final String password) {

    try {
      final Authentication authentication =
          authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)).block();
      final WebSession session = context.getSession().block();
      final SecurityContextImpl securityContext = new SecurityContextImpl();
      securityContext.setAuthentication(authentication);
      // NOTE: Must be exactly attribute as final WebSessionSecurityContextRepository
      session.getAttributes().put("USER", securityContext);
      return Mono.just(AuthUtils.fromAuthentication(authentication));
    } catch (final Exception e) {
      log.warn("Authentication failed for {}", username);
      return Mono.empty();
    }
  }

  @GraphQLQuery(name = "session", description = "Get the user's session id")
  public Mono<String> userSession(@GraphQLContext final User user,
      @GraphQLRootContext final InvestRootContext context) {
    return context.getSession().map(WebSession::getId);
  }

  @GraphQLQuery(name = "user", description = "Get user details")
  public Mono<User> user(@GraphQLRootContext final InvestRootContext context) {
    return context.getAuthentication().map(AuthUtils::fromAuthentication);
  }

  @GraphQLQuery(name = "users", description = "Get all users details")
  public Flux<User> allUsers(@GraphQLRootContext final InvestRootContext context) {
    return securityService.findAccounts().map(AuthUtils::fromAccount);
  }

  @GraphQLMutation(name = "logout", description = "Log out the current session")
  public Mono<Boolean> logout(@GraphQLRootContext final InvestRootContext context) {
    return context.getSession().doOnNext(s -> {
      s.getAttributes().remove("USER");
      s.invalidate();
    }).then(Mono.just(true));
  }

  @GraphQLMutation(name = "saveUser", description = "Create or edit an existing user")
  public Mono<User> saveUser(@GraphQLRootContext final InvestRootContext context,
      @GraphQLArgument(name = "user") final User user, @GraphQLArgument(name = "password") final String password) {

    final Authentication authentication = context.getAuthentication().block();
    if (!AuthUtils.hasAuthority(authentication, InvestRoles.ROLE_ADMINISTRATOR)) {
      log.warn("Attempt by user {} to create or edit user {}", authentication.getName(), user.getUsername());
      return Mono.empty();
    }

    Optional<UserAccount> existingAccount = securityService.getAccount(user.getUsername()).blockOptional();
    Mono<UserAccount> newUser;
    if (existingAccount.isPresent()) {
      newUser = securityService.updateAccount(user.getUsername(), user.getName(), null, user.getRoles());
      if (password != null && !password.isEmpty()) {
        securityService.changePassword(user.getUsername(), password);
      }
    } else {
      newUser = securityService.findOrAddAccount(user.getUsername(), password, user.getName(), null, user.getRoles());
    }

    return newUser.map(AuthUtils::fromAccount);
  }

  @GraphQLMutation(name = "changePassword")
  public void changePassword(@GraphQLRootContext final InvestRootContext context,
      @GraphQLArgument(name = "username") final String username,
      @GraphQLArgument(name = "password") final String password) {

    final Authentication authentication = context.getAuthentication().block();

    if (AuthUtils.hasAuthority(authentication, InvestRoles.ROLE_ADMINISTRATOR)
        || authentication.getName().equals(username)) {
      securityService.changePassword(username, password);
    } else {
      log.warn("Attempt by user {} to change password for {}", authentication.getName(), username);
    }
  }

  @GraphQLMutation(name = "deleteUser", description = "Delete a user")
  public void deleteUser(@GraphQLRootContext final InvestRootContext context,
      @GraphQLArgument(name = "username") final String username) {

    final Authentication authentication = context.getAuthentication().block();

    if (AuthUtils.hasAuthority(authentication, InvestRoles.ROLE_ADMINISTRATOR)) {
      securityService.deleteAccount(username);
      log.info("Deleted user {}", username);
    } else {
      log.warn("Attempt by user {} to delete user {}", authentication.getName(), username);
    }
  }

}
