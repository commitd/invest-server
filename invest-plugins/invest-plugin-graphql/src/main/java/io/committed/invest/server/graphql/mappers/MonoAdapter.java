package io.committed.invest.server.graphql.mappers;

import java.lang.reflect.AnnotatedType;
import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.graphql.execution.GlobalEnvironment;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.generator.mapping.AbstractTypeAdapter;
import io.leangen.graphql.metadata.strategy.value.ValueMapper;
import io.leangen.graphql.util.ClassUtils;
import reactor.core.publisher.Mono;

/**
 * GraphQL Type Convertor for Mono.
 *
 * As of 0.9.6 SPRQ does have support for Publisher, though Flux and Mono are more powerful and
 * intuitive.
 *
 * Based on Optional adaptor in SPQR.
 */
public class MonoAdapter<T> extends AbstractTypeAdapter<Mono<T>, T> {

  @Override
  public T convertOutput(final Mono<T> original, final AnnotatedType type,
      final ResolutionEnvironment resolutionEnvironment) {
    return (T) original
        .map(inner -> resolutionEnvironment.convertOutput(inner, getSubstituteType(type))).block();
  }

  @Override
  public AnnotatedType getSubstituteType(final AnnotatedType original) {
    final AnnotatedType innerType =
        GenericTypeReflector.getTypeParameter(original, Mono.class.getTypeParameters()[0]);
    return ClassUtils.addAnnotations(innerType, original.getAnnotations());
  }

  @Override
  public Mono<T> convertInput(final T substitute, final AnnotatedType type, final GlobalEnvironment environment,
      final ValueMapper valueMapper) {
    return Mono.justOrEmpty(environment.convertInput(substitute, getSubstituteType(type), valueMapper));
  }
}
