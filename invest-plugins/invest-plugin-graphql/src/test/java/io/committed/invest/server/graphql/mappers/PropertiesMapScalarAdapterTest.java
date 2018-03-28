package io.committed.invest.server.graphql.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.util.Map;

import org.junit.Test;
import org.mockito.ArgumentMatchers;

import reactor.core.publisher.Flux;
import graphql.schema.DataFetchingEnvironmentBuilder;

import io.committed.invest.core.dto.collections.PropertiesMap;

import io.leangen.geantyref.TypeFactory;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.metadata.strategy.value.ValueMapper;

public class PropertiesMapScalarAdapterTest {

  @Test
  public void test() {
    final AnnotatedType type =
        TypeFactory.parameterizedAnnotatedClass(Flux.class, new Annotation[0]);
    final ValueMapper valueMapper = mock(ValueMapper.class);
    when(valueMapper.fromInput(
            ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
        .thenAnswer(i -> i.getArgument(0));
    final ResolutionEnvironment resolutionEnvironment =
        new ResolutionEnvironment(new DataFetchingEnvironmentBuilder().build(), valueMapper, null);

    final PropertiesMapScalarAdapter adapter = new PropertiesMapScalarAdapter();

    final PropertiesMap original = new PropertiesMap();
    original.add("a", 23);
    final Map<String, Object> output =
        (Map<String, Object>) adapter.convertOutput(original, type, resolutionEnvironment);

    assertThat(output).containsEntry("a", new Integer(23));
  }
}
