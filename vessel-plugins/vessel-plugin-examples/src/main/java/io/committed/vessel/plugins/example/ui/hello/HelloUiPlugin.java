package io.committed.vessel.plugins.example.ui.hello;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.vessel.actions.ActionDefinition;
import io.committed.vessel.actions.SimpleActionDefinition;
import io.committed.vessel.extensions.VesselUiExtension;
import lombok.Data;

public class HelloUiPlugin implements VesselUiExtension {

  @Override
  public String getStaticResourcePath() {
    return "/ui-hello/";
  }

  @Override
  public Collection<ActionDefinition> getActions() {
    return Arrays.asList(
        SimpleActionDefinition.builder().action("documents.view").description("View a document")
            .title("Say Hello").payload(ExamplePayload.class).build());
  }

  public static class ExamplePayload {
    public String documentId;
  }

  @Override
  public Class<?> getSettings() {
    return Settings.class;
  }

  @Override
  public Class<?> getConfiguration() {
    return PluginConfiguration.class;
  }

  @Configuration
  @Import(Settings.class)
  public static class PluginConfiguration {

  }

  @Configuration
  @ConfigurationProperties("vessel.plugin.ui.example.hello")
  @Data
  public static class Settings {
    private String hello = "Hi";
  }

}
