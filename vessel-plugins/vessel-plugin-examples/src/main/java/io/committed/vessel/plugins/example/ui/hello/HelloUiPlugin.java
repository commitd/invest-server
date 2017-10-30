package io.committed.vessel.plugins.example.ui.hello;

import java.util.Arrays;
import java.util.Collection;

import io.committed.vessel.actions.ActionDefinition;
import io.committed.vessel.actions.SimpleActionDefinition;
import io.committed.vessel.extensions.VesselUiExtension;

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

}
