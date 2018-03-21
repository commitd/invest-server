package io.committed.invest.plugins.ui.host;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import io.committed.invest.core.services.UiUrlService;
import io.committed.invest.extensions.InvestUiExtension;
import io.committed.invest.plugins.ui.host.UiHostExtensionTest.Configuration;
import io.committed.invest.test.InvestTestContext;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Configuration.class, UiHostExtension.class, InvestTestContext.class})
public class UiHostExtensionTest {

  @Autowired
  UiHostExtension extension;

  @Test
  public void test() {
    assertThat(extension).isNotNull();

    assertThat(extension.getName()).isNotBlank();
    assertThat(extension.getDescription()).isNotBlank();
    assertThat(extension.getId()).isNotBlank();
  }

  @TestConfiguration
  public static class Configuration {


    // We return a UriUrlService which would normally we provided by the app
    @Bean
    public UiUrlService urlService() {
      return new UiUrlService() {

        @Override
        public boolean isPathForExtensionRoot(final String path) {
          return false;
        }

        @Override
        public String getContextRelativePath(final InvestUiExtension extension) {
          return extension.getId();
        }

        @Override
        public String getContextPath() {
          return "/test";
        }
      };
    }
  }
}
