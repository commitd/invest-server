package io.committed.vessel.graphql.ui;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("vessel.ui")
public class UiPluginsSettings {


  /**
   * Specify the plugins order of plugins. in the UI.
   *
   * Any available plugins not on this list will be at the end (and unordered relevative to one
   * another).
   */
  private List<String> plugins = null;
}
