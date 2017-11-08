package io.committed.vessel.plugins.ui.livedev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
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
public class LiveDevelopmentUIConfig {

  @Autowired
  UiUrlService urlService;

  // TODO: I want to inject this but it not defined of this type... rather VesselUiExtension (need
  // to sort that out)
  LiveDevelopmentUIPlugin plugin = new LiveDevelopmentUIPlugin();

  @Bean
  public RouterFunction<?> routerFunction() {
    // We push out any requests here to another proxy
    final String fullPath = urlService.getFullPath(plugin);
    return RouterFunctions
        .route(RequestPredicates.path(fullPath + "/**"), this::handle)
        .andRoute(RequestPredicates.path("/static/**"), this::handle)
        .andRoute(RequestPredicates.path("/sockjs-node/**"), this::handle)
        .andRoute(RequestPredicates.path("/__webpack_dev_server__/**"), this::handle);
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

    return requestSpec
        .uri(info.build())
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
