package io.committed.invest.plugins.example.ui.hello;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@ConfigurationProperties("vessel.plugin.ui.example.hello")
@Data
public class HelloUiPluginSettings {
  private String message = "Hi";
}
