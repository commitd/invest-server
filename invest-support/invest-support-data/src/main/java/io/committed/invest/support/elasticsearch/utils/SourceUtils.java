package io.committed.invest.support.elasticsearch.utils;

import java.util.Objects;

import org.elasticsearch.action.search.SearchResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class SourceUtils {

  private SourceUtils() {
    // Singleton
  }

  public static <T> Flux<T> convertHits(final ObjectMapper mapper, final SearchResponse r,
      final Class<T> clazz) {

    // NOTE this loses id

    return Flux.just(r.getHits().getHits())
        .flatMap(h -> convertSource(mapper, h.getSourceAsString(), clazz))
        .filter(Objects::nonNull);

  }

  public static <T> Mono<T> convertSource(final ObjectMapper mapper, final String source,
      final Class<T> clazz) {
    try {
      return Mono.just(mapper.readValue(source, clazz));
    } catch (final Exception e) {
      log.warn("Unable to deserialise source", e);
      return Mono.empty();
    }
  }
}
