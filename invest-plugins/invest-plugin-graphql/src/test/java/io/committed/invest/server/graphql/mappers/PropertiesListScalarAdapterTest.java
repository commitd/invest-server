package io.committed.invest.server.graphql.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.util.List;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import graphql.schema.DataFetchingEnvironmentBuilder;
import io.committed.invest.core.dto.collections.PropertiesList;
import io.committed.invest.core.dto.collections.Property;
import io.leangen.geantyref.TypeFactory;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.metadata.strategy.value.ValueMapper;
import reactor.core.publisher.Flux;

public class PropertiesListScalarAdapterTest {

  @Test
  public void test() {
    final AnnotatedType type = TypeFactory.parameterizedAnnotatedClass(Flux.class, new Annotation[0]);
    final ValueMapper valueMapper = mock(ValueMapper.class);
    when(valueMapper.fromInput(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
        .thenAnswer(i -> i.getArgument(0));
    final ResolutionEnvironment resolutionEnvironment =
        new ResolutionEnvironment(new DataFetchingEnvironmentBuilder().build(), valueMapper, null);

    final PropertiesListScalarAdapter adapter = new PropertiesListScalarAdapter();

    final PropertiesList original = new PropertiesList();
    original.add("a", 23);
    final List<Property> output = adapter.convertOutput(original, type, resolutionEnvironment);

    assertThat(output).containsExactly(new Property("a", 23));
  }

}
