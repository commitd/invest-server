package io.committed.invest.server.graphql.mappers;

import java.lang.reflect.AnnotatedType;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.geantyref.TypeFactory;
import io.leangen.graphql.execution.GlobalEnvironment;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.generator.mapping.AbstractTypeAdapter;
import io.leangen.graphql.metadata.strategy.value.ValueMapper;

/**
 * GraphQL Type Convertor for Flux.
 *
 * <p>As of 0.9.6 SPRQ does have support for Publisher, though Flux and Mono are more powerful and
 * intuitive.
 *
 * <p>Based on Stream To Collection convertors in SPQR.
 */
@Slf4j
public class FluxToCollectionTypeAdapter<T> extends AbstractTypeAdapter<Flux<T>, List<T>> {

  @Override
  public List<T> convertOutput(
      final Flux<T> original,
      final AnnotatedType type,
      final ResolutionEnvironment resolutionEnvironment) {
    try {
      return original
          .map(item -> resolutionEnvironment.<T, T>convertOutput(item, getElementType(type)))
          .collect(Collectors.toList())
          .block();
    } catch (final Exception e) {
      log.error("Unable to convert flux {}, returning empty", e.getMessage());
      log.debug("Exception was:", e);
      return Collections.emptyList();
    }
  }

  @Override
  public Flux<T> convertInput(
      final List<T> substitute,
      final AnnotatedType type,
      final GlobalEnvironment environment,
      final ValueMapper valueMapper) {
    return Flux.fromIterable(substitute)
        .map(item -> environment.convertInput(item, getElementType(type), valueMapper));
  }

  @Override
  public AnnotatedType getSubstituteType(final AnnotatedType original) {
    return TypeFactory.parameterizedAnnotatedClass(
        List.class, original.getAnnotations(), getElementType(original));
  }

  private AnnotatedType getElementType(final AnnotatedType type) {
    return GenericTypeReflector.getTypeParameter(type, Flux.class.getTypeParameters()[0]);
  }
}
