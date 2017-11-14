package io.committed.vessel.plugins.example.ui.hello;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.stereotype.Component;

import io.committed.vessel.actions.ActionDefinition;
import io.committed.vessel.actions.SimpleActionDefinition;
import io.committed.vessel.extensions.VesselUiExtension;

@Component
public class HelloUiPlugin implements VesselUiExtension {

  @Override
  public Collection<ActionDefinition> getActions() {
    return Arrays.asList(
        SimpleActionDefinition.builder()
            .action("documents.view")
            .description("View a document")
            .title("Say Hello")
            .payload(ExamplePayload.class)
            .build());
  }


  @Override
  public String getStaticResourcePath() {
    return "/ui-hello/";
  }

  @Override
  public String getName() {
    return "Hello";
  }

  @Override
  public String getDescription() {
    return "Says hello to you";
  }

  @Override
  public Class<?> getSettings() {
    return HelloUiPluginSettings.class;
  }

  public static class ExamplePayload {
    public String documentId;
  }


}
