package io.committed.invest.server.graphql.mappers;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import io.committed.invest.core.dto.collections.PropertiesMap;
import graphql.schema.GraphQLScalarType;
import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.generator.BuildContext;
import io.leangen.graphql.generator.OperationMapper;
import io.leangen.graphql.generator.mapping.OutputConverter;
import io.leangen.graphql.generator.mapping.common.CachingMapper;
import io.leangen.graphql.util.Scalars;

/**
 * A dedicated mapper to convert {@link PropertiesMap} from/to a scalar.
 *
 * <p>
 * This is necessary because of the Object values in the Properties. Allowing SPQR to 'ignore' the
 * content of the PropertiesList and pass it through verbatim as JSON matches the intend of
 * PropertiesList/Map which is a catch all for 'stuff' we don't.
 *
 * <p>
 * Based on SPQR's from ObjectScalarAdapter
 */
public class PropertiesMapScalarAdapter extends CachingMapper<GraphQLScalarType, GraphQLScalarType>
implements OutputConverter<PropertiesMap, Map<String, ?>> {

  private static final AnnotatedType MAP = GenericTypeReflector.annotate(LinkedHashMap.class);


  @Override
  public Map<String, ?> convertOutput(
      final PropertiesMap original,
      final AnnotatedType type,
      final ResolutionEnvironment resolutionEnvironment) {
    return resolutionEnvironment.valueMapper.fromInput(original.asMap(), type.getType(), MAP);
  }

  @Override
  public boolean supports(final AnnotatedType type) {
    final Type actualType = type.getType();
    return actualType.equals(PropertiesMap.class);
  }

  @Override
  protected GraphQLScalarType toGraphQLType(final String typeName, final AnnotatedType javaType,
      final OperationMapper operationMapper, final BuildContext buildContext) {
    return Scalars.graphQLObjectScalar(typeName);
  }

  @Override
  protected GraphQLScalarType toGraphQLInputType(final String typeName,
      final AnnotatedType javaType,
      final OperationMapper operationMapper, final BuildContext buildContext) {
    return toGraphQLType(typeName, javaType, operationMapper, buildContext);
  }
}
