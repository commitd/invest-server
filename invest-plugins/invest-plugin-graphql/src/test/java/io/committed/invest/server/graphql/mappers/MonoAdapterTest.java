package io.committed.invest.server.graphql.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.ArgumentMatchers;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import io.leangen.geantyref.TypeFactory;
import io.leangen.graphql.execution.GlobalEnvironment;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.metadata.strategy.value.ValueMapper;

public class MonoAdapterTest {

  @Test
  public void testConvertOutput() {
    final AnnotatedType type =
        TypeFactory.parameterizedAnnotatedClass(Flux.class, new Annotation[0]);
    final ResolutionEnvironment resolutionEnvironment = mock(ResolutionEnvironment.class);
    when(resolutionEnvironment.convertOutput(
            ArgumentMatchers.any(String.class), ArgumentMatchers.any()))
        .then(invocation -> invocation.getArgument(0));

    final MonoAdapter<String> adapter = new MonoAdapter<String>();
    final Mono<String> original = Mono.just("test");
    final String output = adapter.convertOutput(original, type, resolutionEnvironment);

    assertThat(output).isEqualTo("test");
  }

  @Test
  public void testConvertOutputEmpty() {
    final AnnotatedType type =
        TypeFactory.parameterizedAnnotatedClass(Flux.class, new Annotation[0]);
    final ResolutionEnvironment resolutionEnvironment = mock(ResolutionEnvironment.class);
    when(resolutionEnvironment.convertOutput(
            ArgumentMatchers.any(String.class), ArgumentMatchers.any()))
        .then(invocation -> invocation.getArgument(0));

    final MonoAdapter<String> adapter = new MonoAdapter<String>();
    final Mono<String> original = Mono.empty();
    final String output = adapter.convertOutput(original, type, resolutionEnvironment);

    assertThat(output).isNull();
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
        .then(invocation -> Optional.ofNullable(invocation.getArgument(0)));

    final MonoAdapter<String> adapter = new MonoAdapter<String>();
    final String original = "a";
    final Mono<String> output =
        adapter.convertInput(original, type, globalEnvironment, valueMapper);

    assertThat(output.block()).isEqualTo("a");
  }

  @Test
  public void testConvertInputWhenNull() {
    final AnnotatedType type =
        TypeFactory.parameterizedAnnotatedClass(List.class, new Annotation[0]);
    final GlobalEnvironment globalEnvironment = mock(GlobalEnvironment.class);
    final ValueMapper valueMapper = mock(ValueMapper.class);

    when(globalEnvironment.convertInput(
            ArgumentMatchers.any(String.class),
            ArgumentMatchers.any(),
            ArgumentMatchers.eq(valueMapper)))
        .then(invocation -> Optional.ofNullable(invocation.getArgument(0)));

    final MonoAdapter<String> adapter = new MonoAdapter<String>();
    final String original = null;
    final Mono<String> output =
        adapter.convertInput(original, type, globalEnvironment, valueMapper);

    assertThat(output.blockOptional()).isNotPresent();
  }
}
