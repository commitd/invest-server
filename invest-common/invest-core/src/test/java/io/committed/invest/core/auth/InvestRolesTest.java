package io.committed.invest.core.auth;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class InvestRolesTest {

  @Test
  public void testIsAuthorityARole() {
    assertThat(InvestRoles.isAuthorityARole("ROLE_role")).isTrue();

    assertThat(InvestRoles.isAuthorityARole("_role")).isFalse();

  }

  @Test
  public void testFromRoleAuthorityToRole() {
    assertThat(InvestRoles.fromAuthorityToRole("ROLE_role").get()).isEqualTo("role");

    assertThat(InvestRoles.fromAuthorityToRole("role")).isEmpty();
  }

}
