package io.committed.invest.server.core.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.SecureRandom;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.committed.invest.server.core.config.SecureRandomConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecureRandomConfig.class)
public class SecureRandomConfigTest {

  @Autowired(required = false)
  private SecureRandom sr;

  @Autowired(required = false)
  private Random r;

  @Test
  public void test() {
    assertThat(sr).isNotNull();
    assertThat(r).isNotNull();

  }

}
