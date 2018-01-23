package io.committed.invest.plugins.example.ui.hello;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.stereotype.Component;
import io.committed.invest.extensions.InvestUiExtension;
import io.committed.invest.extensions.actions.ActionDefinition;
import io.committed.invest.extensions.actions.SimpleActionDefinition;

@Component
public class HelloUiExtension implements InvestUiExtension {

  @Override
  public Collection<ActionDefinition> getActions() {
    return Arrays.asList(SimpleActionDefinition.builder().action("documents.view")
        .description("View a document").title("Say Hello").payload(ExamplePayload.class).build());
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
