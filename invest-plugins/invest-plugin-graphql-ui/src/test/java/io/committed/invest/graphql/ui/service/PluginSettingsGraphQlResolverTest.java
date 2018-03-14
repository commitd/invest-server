package io.committed.invest.graphql.ui.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.committed.invest.extensions.InvestUiExtension;
import io.committed.invest.extensions.registry.InvestUiExtensionRegistry;
import io.committed.invest.graphql.ui.data.UiPlugin;

public class PluginSettingsGraphQlResolverTest {

  private InvestUiExtensionRegistry uiRegistry;
  private PluginSettingsGraphQlResolver resolver;

  @Before
  public void before() {

    final TestInvestUiExtension present = new TestInvestUiExtension("present");
    final Collection<InvestUiExtension> extensions = Arrays.asList(
        present,
        new TestInvestUiExtension("no-settings"));


    final Map<String, Object> settings = new HashMap<>();
    settings.put("a", "b");
    present.setSettings(settings);

    uiRegistry = new InvestUiExtensionRegistry(extensions);

    resolver = new PluginSettingsGraphQlResolver(uiRegistry, new ObjectMapper());


  }

  @Test
  public void testMissingPlugin() {
    final UiPlugin plugin = new UiPlugin();
    plugin.setId("missing");
    final Optional<String> settings = resolver.settings(plugin).blockOptional();

    assertThat(settings).isNotPresent();
  }

  @Test
  public void testPresentPlugin() {
    final UiPlugin plugin = new UiPlugin();
    plugin.setId("present");
    final Optional<String> settings = resolver.settings(plugin).blockOptional();

    assertThat(settings.get()).isNotBlank();

  }

  @Test
  public void testNoSettingsPlugin() {
    final UiPlugin plugin = new UiPlugin();
    plugin.setId("no-settings");
    final Optional<String> settings = resolver.settings(plugin).blockOptional();

    assertThat(settings).isNotPresent();
  }

}
