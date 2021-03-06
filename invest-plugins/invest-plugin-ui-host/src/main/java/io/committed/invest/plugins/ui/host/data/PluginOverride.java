package io.committed.invest.plugins.ui.host.data;

import java.util.Map;

import lombok.Data;

/** Override ofaspecific plugin's settings. */
@Data
public class PluginOverride {

  private String id;

  private Map<String, Object> settings;
}
