package io.committed.invest.plugin.server.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.google.common.collect.Sets;
import io.committed.invest.core.auth.InvestRoles;
import io.committed.invest.plugin.server.auth.dao.UserAccount;
import io.committed.invest.plugin.server.repo.MapBackedUserAccountRepository;
import io.committed.invest.plugin.server.repo.ReactiveUserAccountRepositoryWrapper;
import io.committed.invest.plugin.server.repo.UserAccountRepository;

/**
 * An in-memory map backed authentication.
 *
 * This is useful for development and testing,
 *
 * It sets up three accounts: admin, dev, and user with corresponding roles of the same name. The
 * passwords are the same as the username .
 *
 */
@Configuration
@Profile({"auth-mem"})
public class MemAuthConfig extends AbstractWithAuthSecurityConfig {

  @Bean
  public UserAccountRepository userDetailsRepository(final PasswordEncoder passwordEncoder) {
    final MapBackedUserAccountRepository repo = new MapBackedUserAccountRepository();

    final UserAccount user = UserAccount.builder()
        .username("user")
        .password(passwordEncoder.encode("user"))
        .authorities(Sets.newHashSet(InvestRoles.ROLE_USER))
        .build();
    final UserAccount admin = UserAccount.builder()
        .username("admin")
        .password(passwordEncoder.encode("admin"))
        .authorities(Sets.newHashSet(InvestRoles.ROLE_ADMINISTRATOR))
        .build();
    final UserAccount dev = UserAccount.builder()
        .username("dev")
        .password(passwordEncoder.encode("dev"))
        .authorities(Sets.newHashSet(InvestRoles.ROLE_DEV))
        .build();

    repo.save(admin);
    repo.save(user);
    repo.save(dev);

    return new ReactiveUserAccountRepositoryWrapper(repo);

  }


}
