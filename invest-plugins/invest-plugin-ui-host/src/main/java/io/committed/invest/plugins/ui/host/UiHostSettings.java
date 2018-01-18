package io.committed.invest.plugins.ui.host;

import java.io.File;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@ConfigurationProperties("invest.ui.host")
@Data
public class UiHostSettings {
  private File root = new File("./ui");

  private String pluginFilename = "invest.json";
}

