package io.committed.vessel.graphql.ui.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.committed.vessel.core.services.UiUrlService;
import io.committed.vessel.extensions.VesselUiExtension;
import io.committed.vessel.graphql.ui.data.UiPlugin;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VesselServerGraphQLService {

  private final List<VesselUiExtension> uiExtensions;
  private final UiUrlService urlService;


  @Autowired
  public VesselServerGraphQLService(final UiUrlService urlService,
      @Autowired(required = false) final List<VesselUiExtension> uiExtensions,
      final ApplicationContext applicationcontext, final ObjectMapper mapper) {
    this.urlService = urlService;
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


}
