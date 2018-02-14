package io.committed.invest.plugins.ui.application;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("invest.app")
public class UiApplicationSettings {

  private String title = "Vessel";

  private String directory = null;


  public boolean isHostedFromFileSystem() {
    return directory != null;
  }
}
