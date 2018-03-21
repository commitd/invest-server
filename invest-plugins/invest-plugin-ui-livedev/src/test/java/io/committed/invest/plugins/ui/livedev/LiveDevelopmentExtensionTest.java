package io.committed.invest.plugins.ui.livedev;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import io.committed.invest.core.auth.InvestAuthorities;
import io.committed.invest.core.services.UiUrlService;
import io.committed.invest.test.InvestTestContext;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {LiveDevelopmentUIExtension.class, InvestTestContext.class})
public class LiveDevelopmentExtensionTest {

  @Autowired
  LiveDevelopmentUIExtension extension;

  @MockBean
  UiUrlService urlService;

  @Test
  public void test() {
    assertThat(extension).isNotNull();

    assertThat(extension.getName()).isNotBlank();
    assertThat(extension.getDescription()).isNotBlank();
    assertThat(extension.getId()).isNotBlank();
    assertThat(extension.getRoles()).containsExactly(InvestAuthorities.DEV);
    assertThat(extension.getIcon()).isNotBlank();

  }

}
