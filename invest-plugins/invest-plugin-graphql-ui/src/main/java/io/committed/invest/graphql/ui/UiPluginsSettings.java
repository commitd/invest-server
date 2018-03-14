package io.committed.invest.graphql.ui;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

/**
 * Settings for the UI plugins
 */
@Data
@ConfigurationProperties("vessel.ui")
public class UiPluginsSettings {


  /**
   * Specify the plugins order of plugins in the UI.
   *
   * Any available plugins not on this list will be placed at the end (and unordered relative to one
   * another).
   */
  private List<String> plugins = null;
}
