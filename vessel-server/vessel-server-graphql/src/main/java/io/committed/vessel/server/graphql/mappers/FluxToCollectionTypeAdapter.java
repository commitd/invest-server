package io.committed.vessel.server.graphql.mappers;


import java.lang.reflect.AnnotatedType;
import java.util.List;
import java.util.stream.Collectors;

import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.geantyref.TypeFactory;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.generator.mapping.AbstractTypeAdapter;
import reactor.core.publisher.Flux;

/**
 * Based on StreamToCollectionType
 */
public class FluxToCollectionTypeAdapter extends AbstractTypeAdapter<Flux<?>, List<?>> {

  @Override
  public List<?> convertOutput(final Flux<?> flux, final AnnotatedType type,
      final ResolutionEnvironment resolutionEnvironment) {
    return flux
        .map(item -> resolutionEnvironment.convertOutput(item, getElementType(type)))
        .collect(Collectors.toList())
        .block();
  }

  @Override
  public Flux<?> convertInput(final List<?> substitute, final AnnotatedType type,
      final ResolutionEnvironment resolutionEnvironment) {
    return Flux.fromIterable(substitute)
        .map(item -> resolutionEnvironment.convertInput(item, getElementType(type)));
  }

  @Override
  public AnnotatedType getSubstituteType(final AnnotatedType original) {
    return TypeFactory.parameterizedAnnotatedClass(List.class, original.getAnnotations(),
        getElementType(original));
  }

  private AnnotatedType getElementType(final AnnotatedType type) {
    return GenericTypeReflector.getTypeParameter(type, Flux.class.getTypeParameters()[0]);
  }
}
