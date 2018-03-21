package io.committed.invest.server.core;

import static org.assertj.core.api.Assertions.assertThat;
import java.security.SecureRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import io.committed.invest.test.InvestTestContext;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ServerCoreConfiguration.class, InvestTestContext.class})
public class ServerCoreConfigurationTest {

  @Autowired
  SecureRandom random;

  @Test
  public void test() {
    assertThat(random).isNotNull();
  }
}
