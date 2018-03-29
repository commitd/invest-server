package io.committed.invest.graphql.ui;

import java.util.List;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/** Settings for the UI plugins */
@Data
@ConfigurationProperties("invest.ui")
public class UiPluginsSettings {

  /**
   * Specify the plugins order of plugins in the UI.
   *
   * <p>Any available plugins not on this list will be placed at the end (and unordered relative to
   * one another).
   */
  private List<String> plugins = null;
}
