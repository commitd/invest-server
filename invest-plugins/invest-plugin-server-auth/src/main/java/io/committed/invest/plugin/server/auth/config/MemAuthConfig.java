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

    final UserAccount user = UserAccount.builder().username("user").password(passwordEncoder.encode("user"))
        .authorities(Sets.newHashSet(InvestRoles.USER_AUTHORITY)).build();
    final UserAccount admin = UserAccount.builder().username("admin").password(passwordEncoder.encode("admin"))
        .authorities(Sets.newHashSet(InvestRoles.ADMINISTRATOR_AUTHORITY)).build();
    final UserAccount dev = UserAccount.builder().username("dev").password(passwordEncoder.encode("dev"))
        .authorities(Sets.newHashSet(InvestRoles.DEV_AUTHORITY)).build();
    final UserAccount devadmin = UserAccount.builder().username("devadmin").password(passwordEncoder.encode("devadmin"))
        .authorities(Sets.newHashSet(InvestRoles.DEV_AUTHORITY, InvestRoles.ADMINISTRATOR_AUTHORITY)).build();
    final UserAccount devuser = UserAccount.builder().username("devuser").password(passwordEncoder.encode("devuser"))
        .authorities(Sets.newHashSet(InvestRoles.DEV_AUTHORITY, InvestRoles.ADMINISTRATOR_AUTHORITY)).build();

    repo.save(admin);
    repo.save(user);
    repo.save(dev);
    repo.save(devadmin);
    repo.save(devuser);

    return new ReactiveUserAccountRepositoryWrapper(repo);

  }


}
