package io.committed.invest.graphql.ui.service;

import org.springframework.beans.factory.annotation.Autowired;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.committed.invest.extensions.annotations.GraphQLService;
import io.committed.invest.extensions.registry.InvestUiExtensionRegistry;
import io.committed.invest.graphql.ui.data.UiPlugin;

import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

/** Resolver which returns the configuration settings for a plugin. */
@GraphQLService
public class PluginSettingsGraphQlResolver {

  private final ObjectMapper mapper;
  private final InvestUiExtensionRegistry uiRegistry;

  @Autowired
  public PluginSettingsGraphQlResolver(
      @Autowired(required = false) final InvestUiExtensionRegistry uiRegistry,
      final ObjectMapper mapper) {
    this.uiRegistry = uiRegistry;
    this.mapper = mapper;
  }

  @GraphQLQuery(name = "settings", description = "Plugin settings ")
  public Mono<String> settings(@GraphQLContext final UiPlugin uiPlugin) {

    return Flux.fromStream(uiRegistry.stream())
        .filter(p -> p.getId().equalsIgnoreCase(uiPlugin.getId()))
        .next()
        .flatMap(p -> Mono.justOrEmpty(p.getSettings()))
        .flatMap(
            v -> {
              try {
                return Mono.just(mapper.writerFor(v.getClass()).writeValueAsString(v));
              } catch (final Exception e) {
                return Mono.empty();
              }
            });
  }
}
