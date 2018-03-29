package io.committed.invest.plugins.ui.application;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/** Settings which related to the hosting of the main UI. */
@Data
@ConfigurationProperties("invest.app")
public class ApplicationSettings {

  private String directory = null;

  public boolean isHostedFromFileSystem() {
    return directory != null;
  }
}
