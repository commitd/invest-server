package io.committed.invest.plugins.ui.application;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("invest.app")
public class ApplicationSettings {

  private String directory = null;

  public boolean isHostedFromFileSystem() {
    return directory != null;
  }


}
