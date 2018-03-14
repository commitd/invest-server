package io.committed.invest.server.graphql.mappers;


import java.lang.reflect.AnnotatedType;
import java.util.List;
import java.util.stream.Collectors;
import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.geantyref.TypeFactory;
import io.leangen.graphql.execution.GlobalEnvironment;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.generator.mapping.AbstractTypeAdapter;
import io.leangen.graphql.metadata.strategy.value.ValueMapper;
import reactor.core.publisher.Flux;

/**
 * GraphQL Type Convertor for Flux.
 *
 * As of 0.9.6 SPRQ does have support for Publisher, though Flux and Mono are more powerful and
 * intuitive.
 *
 * Based on Stream To Collection convertors in SPQR.
 */
public class FluxToCollectionTypeAdapter<T> extends AbstractTypeAdapter<Flux<T>, List<T>> {

  @Override
  public List<T> convertOutput(final Flux<T> original, final AnnotatedType type,
      final ResolutionEnvironment resolutionEnvironment) {
    return original
        .map(item -> resolutionEnvironment.<T, T>convertOutput(item, getElementType(type)))
        .collect(Collectors.toList())
        .block();
  }

  @Override
  public Flux<T> convertInput(final List<T> substitute, final AnnotatedType type, final GlobalEnvironment environment,
      final ValueMapper valueMapper) {
    return Flux.fromIterable(substitute).map(item -> environment.convertInput(item, getElementType(type), valueMapper));
  }

  @Override
  public AnnotatedType getSubstituteType(final AnnotatedType original) {
    return TypeFactory.parameterizedAnnotatedClass(List.class, original.getAnnotations(), getElementType(original));
  }

  private AnnotatedType getElementType(final AnnotatedType type) {
    return GenericTypeReflector.getTypeParameter(type, Flux.class.getTypeParameters()[0]);
  }
}
