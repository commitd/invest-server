package io.committed.vessel.plugin.server.auth.graphql;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.committed.vessel.core.dto.auth.VesselUser;
import io.committed.vessel.extensions.graphql.VesselGraphQlService;
import io.committed.vessel.plugin.server.auth.constants.VesselRoles;
import io.committed.vessel.plugin.server.services.UserService;
import lombok.extern.slf4j.Slf4j;

@VesselGraphQlService
@Slf4j
public class AuthController {

  private final UserService securityService;

  public AuthController(final UserService securityService) {
    this.securityService = securityService;
  }

  public VesselUser login(final Principal user) {
    if (user == null) {
      return null;
    }

    Set<String> roles;

    if (user instanceof Authentication) {
      final Authentication auth = (Authentication) user;
      roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
          .collect(Collectors.toSet());
    } else {
      roles = Collections.emptySet();
    }

    return new VesselUser(user.getName(), true, roles);
  }


  public void changePassword(final Authentication authentication,
      final String username, final String password) {

    if (securityService.hasAuthoritory(authentication, VesselRoles.ADMINISTRATOR_AUTHORITY)
        || authentication.getName().equalsIgnoreCase(username)) {
      securityService.changePassword(username, password);
    } else {
      log.warn("Attempt by user {} to change password for {}",
          authentication.getName(), username);
    }
  }
}
