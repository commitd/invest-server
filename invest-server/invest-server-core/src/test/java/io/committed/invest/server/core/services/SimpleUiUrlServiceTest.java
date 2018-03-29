package io.committed.invest.server.core.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import io.committed.invest.extensions.InvestUiExtension;

public class SimpleUiUrlServiceTest {

  @Test
  public void test() {
    final SimpleUiUrlService service = new SimpleUiUrlService();
    assertThat(service.getContextPath()).isNotBlank();
  }

  @Test
  public void testDifferentPaths() {
    final SimpleUiUrlService service = new SimpleUiUrlService();

    final InvestUiExtension a = mock(InvestUiExtension.class);
    doReturn("id1").when(a).getId();
    final InvestUiExtension b = mock(InvestUiExtension.class);
    doReturn("id2").when(b).getId();

    final String aPath = service.getContextRelativePath(a);
    final String bPath = service.getContextRelativePath(b);
    assertThat(aPath).isNotBlank();
    assertThat(bPath).isNotBlank();

    assertThat(aPath).isNotEqualTo(bPath);
  }

  @Test
  public void testContextPaths() {
    final SimpleUiUrlService service = new SimpleUiUrlService();

    final InvestUiExtension a = mock(InvestUiExtension.class);
    doReturn("id1").when(a).getId();

    assertThat(service.getFullPath(a)).startsWith(service.getContextPath());
    assertThat(service.getFullPath(a)).endsWith(service.getContextRelativePath(a));
  }

  @Test
  public void testSubFiles() {
    final SimpleUiUrlService service = new SimpleUiUrlService();

    final InvestUiExtension a = mock(InvestUiExtension.class);
    doReturn("id1").when(a).getId();

    final String aPath = service.getFullPath(a);

    assertThat(service.isPathForExtensionRoot(aPath)).isTrue();
    assertThat(service.isPathForExtensionRoot(aPath + "/index.html")).isFalse();
    assertThat(service.isPathForExtensionRoot(aPath + "/static/example.jpg")).isFalse();
  }
}
