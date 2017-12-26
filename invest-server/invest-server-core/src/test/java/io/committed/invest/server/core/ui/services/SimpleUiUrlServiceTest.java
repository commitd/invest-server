package io.committed.invest.server.core.ui.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import io.committed.invest.extensions.InvestUiExtension;

public class SimpleUiUrlServiceTest {

  @Test
  public void testContextPath() {
    final SimpleUiUrlService service = new SimpleUiUrlService();

    assertThat(service.getContextPath()).startsWith("/");
  }

  @Test
  public void testContextRelativePath() {
    final SimpleUiUrlService service = new SimpleUiUrlService();

    final InvestUiExtension extension = new InvestUiExtension() {
      @Override
      public String getId() {
        return "testui";
      }
    };
    assertThat(service.getContextRelativePath(extension)).startsWith("/");
    assertThat(service.getContextRelativePath(extension)).contains(extension.getId());
  }

  @Test
  public void testFullPath() {
    final SimpleUiUrlService service = new SimpleUiUrlService();

    final InvestUiExtension extension = new InvestUiExtension() {
      @Override
      public String getId() {
        return "testui";
      }
    };
    assertThat(service.getFullPath(extension)).startsWith(service.getContextPath());
    assertThat(service.getFullPath(extension)).contains(extension.getId());
  }


}
