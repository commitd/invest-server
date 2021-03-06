package io.committed.invest.support.elasticsearch.utils;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

import org.elasticsearch.action.search.SearchResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.databind.ObjectMapper;

/** Helpers to convert ES's JSON source results to POJO */
@Slf4j
public class SourceUtils {

  private SourceUtils() {
    // Singleton
  }

  public static <T> Flux<T> convertHits(
      final ObjectMapper mapper, final SearchResponse r, final Class<T> clazz) {

    // NOTE this loses id

    return Flux.just(r.getHits().getHits())
        .flatMap(h -> convertSource(mapper, h.getSourceAsString(), clazz))
        .filter(Objects::nonNull);
  }

  public static <T> Mono<T> convertSource(
      final ObjectMapper mapper, final String source, final Class<T> clazz) {
    try {
      return Mono.just(mapper.readValue(source, clazz));
    } catch (final Exception e) {
      log.warn("Unable to deserialise source", e);
      return Mono.empty();
    }
  }
}
