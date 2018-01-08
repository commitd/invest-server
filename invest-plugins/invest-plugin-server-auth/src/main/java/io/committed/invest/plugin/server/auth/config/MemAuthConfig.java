package io.committed.invest.plugin.server.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.google.common.collect.Sets;
import io.committed.invest.plugin.server.auth.dao.UserAccount;
import io.committed.invest.plugin.server.services.MapBackedUserAccountRepository;
import io.committed.invest.plugin.server.services.ReactiveUserAccountRepositoryWrapper;
import io.committed.invest.plugin.server.services.UserAccountRepository;

@Configuration
@Profile({"auth-mem"})
public class MemAuthConfig extends AbstractWithAuthSecurityConfig {

  @Bean
  public UserAccountRepository userDetailsRepository(final PasswordEncoder passwordEncoder) {
    final MapBackedUserAccountRepository repo = new MapBackedUserAccountRepository();

    final UserAccount user = UserAccount.builder().username("user")
        .password(passwordEncoder.encode("user")).authorities(Sets.newHashSet("ROLE_USER")).build();
    final UserAccount admin =
        UserAccount.builder().username("admin").password(passwordEncoder.encode("admin"))
            .authorities(Sets.newHashSet("ROLE_ADMIN")).build();

    repo.save(admin);
    repo.save(user);

    return new ReactiveUserAccountRepositoryWrapper(repo);

  }


}
