package io.committed.invest.graphql.ui.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import io.committed.invest.core.services.UiUrlService;
import io.committed.invest.extensions.InvestUiExtension;
import io.committed.invest.extensions.annotations.GraphQLService;
import io.committed.invest.extensions.graphql.InvestServerNode;
import io.committed.invest.extensions.registry.InvestUiExtensionRegistry;
import io.committed.invest.graphql.ui.UiPluginsSettings;
import io.committed.invest.graphql.ui.data.UiPlugin;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

/** Resolver to list or find plugins for the UI. */
@GraphQLService
public class AvailablePluginsGraphQlResolver {

  private final Collection<InvestUiExtension> uiExtensions;
  private final UiUrlService urlService;

  @Autowired
  public AvailablePluginsGraphQlResolver(
      final UiUrlService urlService,
      @Autowired(required = false) final InvestUiExtensionRegistry uiRegistry,
      final UiPluginsSettings uiPluginSettings) {
    this.urlService = urlService;
    this.uiExtensions = sort(uiRegistry, uiPluginSettings);
  }

  @GraphQLQuery(
    name = "uiPlugins",
    description = "Access details all UI plugins available on the system"
  )
  public Flux<UiPlugin> uiPlugins(@GraphQLContext final InvestServerNode serverNode) {
    return Flux.fromIterable(uiExtensions).map(e -> new UiPlugin(e, urlService.getFullPath(e)));
  }

  @GraphQLQuery(name = "uiPlugin", description = "Access details for a specific UI plugin")
  public Mono<UiPlugin> uiPlugin(
      @GraphQLContext final InvestServerNode serverNode,
      @GraphQLArgument(name = "pluginId", description = "The plugin id to get") final String id) {
    return Flux.fromIterable(uiExtensions)
        .filter(p -> p.getId().equalsIgnoreCase(id))
        .map(e -> new UiPlugin(e, urlService.getFullPath(e)))
        .next();
  }

  private Collection<InvestUiExtension> sort(
      final InvestUiExtensionRegistry uiRegistry, final UiPluginsSettings uiPluginSettings) {

    Collection<InvestUiExtension> collection;
    if (uiRegistry != null) {
      collection = uiRegistry.getExtensions();
    } else {
      collection = Collections.emptyList();
    }

    final List<String> order = uiPluginSettings.getPlugins();
    if (order == null || order.isEmpty()) {
      return collection;
    }

    final Map<String, InvestUiExtension> idToExtension =
        collection.stream().collect(Collectors.toMap(InvestUiExtension::getId, v -> v));
    final List<InvestUiExtension> ordered = new ArrayList<>(collection.size());

    // Add the ones in the order list, remove any from the map so we have just wantever has not been
    // used (in case they've added it twice etc)
    order
        .stream()
        .map(idToExtension::get)
        .filter(Objects::nonNull)
        .forEach(
            e -> {
              idToExtension.remove(e.getId());
              ordered.add(e);
            });

    // Add any left at the end, sorting by alphabetical name just so its consistent to the user
    idToExtension
        .values()
        .stream()
        .sorted((a, b) -> a.getName().compareTo(b.getName()))
        .forEach(ordered::add);

    return ordered;
  }
}
