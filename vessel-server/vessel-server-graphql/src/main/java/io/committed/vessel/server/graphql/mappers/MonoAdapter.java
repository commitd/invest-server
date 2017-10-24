package io.committed.vessel.server.graphql.mappers;

import java.lang.reflect.AnnotatedType;

import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.generator.mapping.AbstractTypeAdapter;
import io.leangen.graphql.util.ClassUtils;
import reactor.core.publisher.Mono;

/**
 * * Based on OptionalAdapter
 *
 */
public class MonoAdapter extends AbstractTypeAdapter<Mono<?>, Object> {

  @Override
  public Object convertOutput(final Mono<?> original, final AnnotatedType type,
      final ResolutionEnvironment resolutionEnvironment) {
    return original
        .map(inner -> resolutionEnvironment.convertOutput(inner, getSubstituteType(type)))
        .block();
  }

  @Override
  public Mono<?> convertInput(final Object substitute, final AnnotatedType type,
      final ResolutionEnvironment resolutionEnvironment) {
    return Mono
        .justOrEmpty(resolutionEnvironment.convertInput(substitute, getSubstituteType(type)));
  }

  @Override
  public AnnotatedType getSubstituteType(final AnnotatedType original) {
    final AnnotatedType innerType =
        GenericTypeReflector.getTypeParameter(original, Mono.class.getTypeParameters()[0]);
    return ClassUtils.addAnnotations(innerType, original.getAnnotations());
  }
}
