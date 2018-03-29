package io.committed.invest.graphql.ui.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import io.committed.invest.core.services.UiUrlService;
import io.committed.invest.extensions.graphql.InvestServerNode;
import io.committed.invest.extensions.registry.InvestUiExtensionRegistry;
import io.committed.invest.graphql.ui.UiPluginsSettings;
import io.committed.invest.graphql.ui.data.UiPlugin;

public class AvailablePluginsGraphQlResolverTest {

  private AvailablePluginsGraphQlResolver resolver;

  @Before
  public void before() {

    final UiUrlService urlService = mock(UiUrlService.class);
    final InvestUiExtensionRegistry uiRegistry =
        new InvestUiExtensionRegistry(
            Arrays.asList(
                new TestInvestUiExtension("plugin-1"), new TestInvestUiExtension("plugin-2")));

    final UiPluginsSettings uiPluginSettings = new UiPluginsSettings();

    resolver = new AvailablePluginsGraphQlResolver(urlService, uiRegistry, uiPluginSettings);
  }

  @Test
  public void testUiPlugins() {
    final List<UiPlugin> uiPlugin =
        resolver.uiPlugins(new InvestServerNode()).collectList().block();

    assertThat(uiPlugin).hasSize(2);
    assertThat(uiPlugin.get(0).getId()).isEqualTo("plugin-1");
    assertThat(uiPlugin.get(1).getId()).isEqualTo("plugin-2");
  }

  @Test
  public void testUiPluginPresent() {
    final UiPlugin uiPlugin = resolver.uiPlugin(new InvestServerNode(), "plugin-1").block();

    assertThat(uiPlugin.getId()).isEqualTo("plugin-1");
  }

  @Test
  public void testUiPluginMissing() {
    final Optional<UiPlugin> uiPlugin =
        resolver.uiPlugin(new InvestServerNode(), "plugin-none").blockOptional();

    assertThat(uiPlugin).isNotPresent();
  }
}
