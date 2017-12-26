package io.committed.invest.server.core.ui.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import io.committed.invest.extensions.VesselUiExtension;
import io.committed.invest.server.core.ui.services.VesselUiUrlService;

public class VesselUiUrlServiceTest {

  @Test
  public void testContextPath() {
    final VesselUiUrlService service = new VesselUiUrlService();

    assertThat(service.getContextPath()).startsWith("/");
  }

  @Test
  public void testContextRelativePath() {
    final VesselUiUrlService service = new VesselUiUrlService();

    final VesselUiExtension extension = new VesselUiExtension() {
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
    final VesselUiUrlService service = new VesselUiUrlService();

    final VesselUiExtension extension = new VesselUiExtension() {
      @Override
      public String getId() {
        return "testui";
      }
    };
    assertThat(service.getFullPath(extension)).startsWith(service.getContextPath());
    assertThat(service.getFullPath(extension)).contains(extension.getId());
  }


}
