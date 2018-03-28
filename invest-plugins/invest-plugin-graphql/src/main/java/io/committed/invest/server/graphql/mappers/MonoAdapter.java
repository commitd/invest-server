package io.committed.invest.server.graphql.mappers;

import java.lang.reflect.AnnotatedType;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.graphql.execution.GlobalEnvironment;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.generator.mapping.AbstractTypeAdapter;
import io.leangen.graphql.metadata.strategy.value.ValueMapper;
import io.leangen.graphql.util.ClassUtils;

/**
 * GraphQL Type Convertor for Mono.
 *
 * <p>As of 0.9.6 SPRQ does have support for Publisher, though Flux and Mono are more powerful and
 * intuitive.
 *
 * <p>Based on Optional adaptor in SPQR.
 */
@Slf4j
public class MonoAdapter<T> extends AbstractTypeAdapter<Mono<T>, T> {

  @Override
  public T convertOutput(
      final Mono<T> original,
      final AnnotatedType type,
      final ResolutionEnvironment resolutionEnvironment) {
    try {
      return (T)
          original
              .map(inner -> resolutionEnvironment.convertOutput(inner, getSubstituteType(type)))
              .block();
    } catch (final Exception e) {
      log.error("Unable to convert mono {}, returning null", e.getMessage());
      log.debug("Exception was:", e);
      return null;
    }
  }

  @Override
  public AnnotatedType getSubstituteType(final AnnotatedType original) {
    final AnnotatedType innerType =
        GenericTypeReflector.getTypeParameter(original, Mono.class.getTypeParameters()[0]);
    return ClassUtils.addAnnotations(innerType, original.getAnnotations());
  }

  @Override
  public Mono<T> convertInput(
      final T substitute,
      final AnnotatedType type,
      final GlobalEnvironment environment,
      final ValueMapper valueMapper) {
    return Mono.justOrEmpty(
        environment.convertInput(substitute, getSubstituteType(type), valueMapper));
  }
}
