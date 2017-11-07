package io.committed.vessel.plugin.server.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.google.common.collect.Sets;

import io.committed.vessel.plugin.server.auth.dao.UserAccount;
import io.committed.vessel.plugin.server.services.MapBackedUserAccountRepository;
import io.committed.vessel.plugin.server.services.ReactiveUserAccountRepositoryWrapper;
import io.committed.vessel.plugin.server.services.UserAccountRepository;

@Configuration
@Profile({ "auth-mem" })
public class MemAuthConfig extends AbstractWithAuthSecurityConfig {



  @Bean
  public UserAccountRepository userDetailsRepository() {
    final MapBackedUserAccountRepository repo = new MapBackedUserAccountRepository();

    final UserAccount user = UserAccount.builder()
        .username("user")
        .password("user")
        .authorities(Sets.newHashSet("USER"))
        .build();
    final UserAccount admin = UserAccount.builder()
        .username("admin")
        .password("admin")
        .authorities(Sets.newHashSet("ADMIN"))
        .build();

    repo.save(admin);
    repo.save(user);

    return new ReactiveUserAccountRepositoryWrapper(repo);

  }


}
