package io.committed.invest.server.graphql.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentMatchers;

import reactor.core.publisher.Flux;
import io.leangen.geantyref.TypeFactory;
import io.leangen.graphql.execution.GlobalEnvironment;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.metadata.strategy.value.ValueMapper;

public class FluxToCollectionTypeAdapterTest {

  @Test
  public void testConvertOutput() {
    final AnnotatedType type =
        TypeFactory.parameterizedAnnotatedClass(Flux.class, new Annotation[0]);
    final ResolutionEnvironment resolutionEnvironment = mock(ResolutionEnvironment.class);
    when(resolutionEnvironment.convertOutput(
            ArgumentMatchers.any(String.class), ArgumentMatchers.any()))
        .then(invocation -> invocation.getArgument(0));

    final FluxToCollectionTypeAdapter<String> adapter = new FluxToCollectionTypeAdapter<String>();
    final Flux<String> original = Flux.fromIterable(Arrays.asList("a", "b", "c"));
    final List<String> list = adapter.convertOutput(original, type, resolutionEnvironment);

    assertThat(list).containsExactly("a", "b", "c");
  }

  @Test
  public void testConvertInput() {
    final AnnotatedType type =
        TypeFactory.parameterizedAnnotatedClass(List.class, new Annotation[0]);
    final GlobalEnvironment globalEnvironment = mock(GlobalEnvironment.class);
    final ValueMapper valueMapper = mock(ValueMapper.class);

    when(globalEnvironment.convertInput(
            ArgumentMatchers.any(String.class),
            ArgumentMatchers.any(),
            ArgumentMatchers.eq(valueMapper)))
        .then(invocation -> invocation.getArgument(0));

    final FluxToCollectionTypeAdapter<String> adapter = new FluxToCollectionTypeAdapter<String>();
    final List<String> original = Arrays.asList("a", "b", "c");
    final Flux<String> flux = adapter.convertInput(original, type, globalEnvironment, valueMapper);

    assertThat(flux.collectList().block()).containsExactly("a", "b", "c");
  }
}
