package io.committed.invest.graphql.ui.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import reactor.core.publisher.Flux;

import io.committed.invest.extensions.actions.SimpleActionDefinition;
import io.committed.invest.extensions.graphql.InvestUiNode;
import io.committed.invest.graphql.ui.data.UiPlugin;
import io.committed.invest.graphql.ui.service.InvestUiQueryResolver.PluginActionDefinition;
import io.committed.invest.graphql.ui.service.InvestUiQueryResolver.QueryActionInput;
import io.committed.invest.graphql.ui.service.InvestUiQueryResolver.QueryActionOutput;

public class InvestUiQueryResolverTest {

  InvestUiNode uiNode = new InvestUiNode();
  private AvailablePluginsGraphQlResolver availablePlugins;
  private InvestUiQueryResolver resolver;

  @Before
  public void before() {
    availablePlugins = mock(AvailablePluginsGraphQlResolver.class);
    resolver = new InvestUiQueryResolver(availablePlugins);

    final TestInvestUiExtension e1 = new TestInvestUiExtension("plugin-1");
    final TestInvestUiExtension e2 = new TestInvestUiExtension("plugin-2");
    final TestInvestUiExtension e3 = new TestInvestUiExtension("plugin-3");
    final TestInvestUiExtension e4 = new TestInvestUiExtension("plugin-4");

    e1.setActions(
        Arrays.asList(SimpleActionDefinition.builder().action("matching.action").build()));
    e3.setActions(
        Arrays.asList(
            SimpleActionDefinition.builder().action("matching.action").build(),
            SimpleActionDefinition.builder().action("other.action").build()));

    when(availablePlugins.uiPlugins(null))
        .thenReturn(
            Flux.fromIterable(
                Arrays.asList(
                    new UiPlugin(e1, "http://example.com/1"),
                    new UiPlugin(e2, "http://example.com/2"),
                    new UiPlugin(e3, "http://example.com/3"),
                    new UiPlugin(e4, "http://example.com/4"))));
  }

  @Test
  public void testStatus() {
    assertThat(resolver.status(uiNode)).isEqualTo("ok");
  }

  @Test
  public void testEmptyActions() {
    final QueryActionInput input = new QueryActionInput();
    final QueryActionOutput actions = resolver.actions(uiNode, input);
    final List<PluginActionDefinition> definitions = actions.getDefinitions().collectList().block();

    assertThat(definitions).hasSize(0);
  }

  @Test
  public void testMissingActions() {

    final QueryActionInput input = new QueryActionInput();
    input.setAction("missing.action");
    final QueryActionOutput actions = resolver.actions(uiNode, input);
    final List<PluginActionDefinition> definitions = actions.getDefinitions().collectList().block();

    assertThat(definitions).hasSize(0);
  }

  @Test
  public void testMatchingActions() {

    final QueryActionInput input = new QueryActionInput();

    input.setAction("matching.action");
    final QueryActionOutput actions = resolver.actions(uiNode, input);
    final List<PluginActionDefinition> definitions = actions.getDefinitions().collectList().block();

    assertThat(definitions).hasSize(2);
  }
}
