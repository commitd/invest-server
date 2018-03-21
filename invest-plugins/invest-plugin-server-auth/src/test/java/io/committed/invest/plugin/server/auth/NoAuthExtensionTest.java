package io.committed.invest.plugin.server.auth;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import io.committed.invest.core.services.UiUrlService;
import io.committed.invest.test.InvestTestContext;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AuthExtension.class, InvestTestContext.class})
@ActiveProfiles("auth-none")
public class NoAuthExtensionTest {

  @Autowired
  AuthExtension extension;

  @MockBean
  UiUrlService urlService;

  @Test
  public void test() {
    assertThat(extension).isNotNull();

    assertThat(extension.getName()).isNotBlank();
    assertThat(extension.getDescription()).isNotBlank();
    assertThat(extension.getId()).isNotBlank();
  }

}
