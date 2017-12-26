package io.committed.invest.graphql.ui.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ClassUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.committed.invest.extensions.InvestUiExtension;
import io.committed.invest.extensions.graphql.GraphQLService;
import io.committed.invest.graphql.ui.data.UiPlugin;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@GraphQLService
public class UiSettingGraphQlResolver {

  // If I set this with applicationContext strange things happen, as it SQPR tried to use the
  // setting to find derive more types
  private final ApplicationContext applicationcontext;
  private final ObjectMapper mapper;
  private final List<InvestUiExtension> uiExtensions;

  @Autowired
  public UiSettingGraphQlResolver(
      @Autowired(required = false) final List<InvestUiExtension> uiExtensions,
      final ApplicationContext applicationcontext, final ObjectMapper mapper) {
    this.applicationcontext = applicationcontext;
    this.mapper = mapper;
    this.uiExtensions = uiExtensions == null ? Collections.emptyList() : uiExtensions;

  }

  @GraphQLQuery(name = "settings", description = "Plugin settings ")
  public Mono<String> settings(@GraphQLContext final UiPlugin uiPlugin) {
    return Flux.fromIterable(uiExtensions)
        .filter(p -> p.getId().equalsIgnoreCase(uiPlugin.getId()))
        .next()
        .flatMap(p -> Mono.justOrEmpty(p.getSettings()))
        .map(applicationcontext::getBean)
        .flatMap(b -> {
          try {
            // We use ClassUtils here because typically we'll be looking at a COnfiguration class
            // which has been proxy enhanced
            // so we need to get back to the base class in order to know what to serialise.
            return Mono.just(
                mapper.writerFor(ClassUtils.getUserClass(b.getClass())).writeValueAsString(b));
          } catch (final Exception e) {
            return Mono.empty();
          }
        });
  }

}
