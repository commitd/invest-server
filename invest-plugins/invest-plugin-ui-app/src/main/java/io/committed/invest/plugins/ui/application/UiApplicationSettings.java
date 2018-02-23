package io.committed.invest.plugins.ui.application;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("invest.config")
public class UiApplicationSettings {

  private String title = "Invest";

  private Map<String, Object> settings;

}
