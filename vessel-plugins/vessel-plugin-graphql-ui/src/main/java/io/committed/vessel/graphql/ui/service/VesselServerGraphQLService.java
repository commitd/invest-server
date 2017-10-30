package io.committed.vessel.graphql.ui.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.committed.vessel.core.services.UiUrlService;
import io.committed.vessel.extensions.VesselUiExtension;
import io.committed.vessel.graphql.ui.data.UiPlugin;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VesselServerGraphQLService {

  private final List<VesselUiExtension> uiExtensions;
  private final UiUrlService urlService;
  // If I set this with applicationContext strange things happen, as it SQPR tried to use the
  // setting to find derive more types
  private final ApplicationContext applicationcontext;
  private final ObjectMapper mapper;

  @Autowired
  public VesselServerGraphQLService(final UiUrlService urlService,
      @Autowired(required = false) final List<VesselUiExtension> uiExtensions,
      final ApplicationContext applicationcontext, final ObjectMapper mapper) {
    this.urlService = urlService;
    this.applicationcontext = applicationcontext;
    this.mapper = mapper;
    this.uiExtensions = uiExtensions == null ? Collections.emptyList() : uiExtensions;

  }


  @GraphQLQuery
  public Flux<UiPlugin> uiPlugins() {
    // HACK: Adding index.html shouldn't be needed
    return Flux.fromIterable(uiExtensions)
        .map(e -> new UiPlugin(e, urlService.getFullPath(e) + "/index.html"));
  }

  @GraphQLQuery
  public Mono<UiPlugin> uiPlugin(@GraphQLArgument(name = "pluginId") final String id) {
    // HACK: Adding index.html shouldn't be needed
    return Flux.fromIterable(uiExtensions)
        .filter(p -> p.getId().equalsIgnoreCase(id))
        .map(e -> new UiPlugin(e, urlService.getFullPath(e) + "/index.html"))
        .next();
  }

  @GraphQLQuery
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
