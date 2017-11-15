package io.committed.vessel.plugins.ui.livedev;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.ServerResponse.BodyBuilder;
import org.springframework.web.util.UriBuilder;

import io.committed.vessel.core.services.UiUrlService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class LiveDevelopmentUIConfig implements WebFluxConfigurer {

  private static final String[] PATHS = {
      "/static/**",
      "/sockjs-node/**",
      "/__webpack_dev_server__/**"
  };

  @Autowired
  UiUrlService urlService;



  // TODO: I want to inject this but it not defined of this type... rather VesselUiExtension (need
  // to sort that out)
  LiveDevelopmentUIPlugin plugin = new LiveDevelopmentUIPlugin();

  private String getFullPath() {
    return urlService.getFullPath(plugin);

  }

  @Bean
  public RouterFunction<?> routerFunction() {
    // We push out any requests here to another proxy
    RouterFunction<ServerResponse> routes = RouterFunctions
        .route(RequestPredicates.path(getFullPath() + "/**"), this::handle);

    for (final String path : PATHS) {
      routes = routes.andRoute(RequestPredicates.path(path), this::handle);
    }

    return routes;
  }

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    // Each of the above routes needs to have CORS enabled
    // the /ui route is already controlled by UiMerger
    // so we need to add the PATHS

    for (final String path : PATHS) {
      registry.addMapping(path)
          .allowedHeaders("*")
          .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
          .allowedOrigins("*");
    }
  }


  Mono<ServerResponse> handle(final ServerRequest request) {

    // Modify the URI to go to our proxy
    final UriBuilder info =
        request.uriBuilder()
            // TODO: Configurable settings
            .host("localhost")
            .port(3001)
            .scheme("http")
            .userInfo(null);

    final String path = request.path();
    final String fullPath = getFullPath();
    if (path.startsWith(fullPath)) {
      info.replacePath(path.substring(fullPath.length()));
    }

    final WebClient client = WebClient.create();

    final RequestHeadersUriSpec<?> requestSpec;
    switch (request.method()) {
      case GET:
        requestSpec = client.get();
        break;
      case POST:
        requestSpec = client.post();

        break;
      default:
        return ServerResponse.badRequest().build();
    }

    // TODO: Pass through headers, cookies, etc
    // TODO: Pass through body and params

    final URI uri = info.build();

    return requestSpec
        .uri(uri)
        .exchange()
        .flatMap(cr -> {
          if (cr.statusCode() != HttpStatus.OK) {
            return ServerResponse.status(cr.statusCode()).build();
          }

          BodyBuilder response = ServerResponse.ok();
          if (cr.headers().contentLength().isPresent()) {
            response = response.contentLength(cr.headers().contentLength().getAsLong());
          }
          if (cr.headers().contentType().isPresent()) {
            response = response.contentType(cr.headers().contentType().get());
          }

          final Flux<DataBuffer> in = cr.body((inputMessage, context) -> inputMessage.getBody());
          return response.body((outputMessage, context) -> outputMessage.writeWith(in));
        });
  }
}
