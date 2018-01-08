package io.committed.invest.plugins.ui.application;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("vessel.app")
public class UiApplicationSettings {

  private String title = "Vessel";

}
