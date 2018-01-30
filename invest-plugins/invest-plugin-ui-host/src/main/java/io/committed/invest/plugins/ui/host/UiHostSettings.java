package io.committed.invest.plugins.ui.host;

import java.io.File;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@ConfigurationProperties("invest.ui.host")
@Data
public class UiHostSettings {
  private List<File> roots = Collections.singletonList(
      new File("./ui"));

  private String pluginFilename = "invest.json";
}

