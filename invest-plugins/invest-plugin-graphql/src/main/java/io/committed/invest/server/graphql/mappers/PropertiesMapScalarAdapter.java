package io.committed.invest.server.graphql.mappers;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import graphql.schema.GraphQLScalarType;
import io.committed.invest.core.dto.collections.PropertiesMap;
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
 * This is necessary because of the Object values in the Properties. Allowing SPQR to 'ignore' the
 * content of the PropertiesList and pass it through verbatim as JSON matches the intend of
 * PropertiesList/Map which is a catch all for 'stuff' we don't.
 *
 * Based on SPQR's from ObjectScalarMapper
 */
public class PropertiesMapScalarAdapter extends CachingMapper<GraphQLScalarType, GraphQLScalarType>
    implements OutputConverter<PropertiesMap, Map<String, ?>> {

  private static final AnnotatedType MAP = GenericTypeReflector.annotate(LinkedHashMap.class);

  @Override
  public GraphQLScalarType toGraphQLType(final String typeName, final AnnotatedType javaType,
      final Set<Type> abstractTypes, final OperationMapper operationMapper, final BuildContext buildContext) {
    buildContext.knownInputTypes.add(typeName);
    return Scalars.graphQLObjectScalar(typeName);
  }

  @Override
  public GraphQLScalarType toGraphQLInputType(final String typeName, final AnnotatedType javaType,
      final Set<Type> abstractTypes, final OperationMapper operationMapper, final BuildContext buildContext) {
    buildContext.knownTypes.add(typeName);
    return toGraphQLType(typeName, javaType, abstractTypes, operationMapper, buildContext);
  }

  @Override
  protected void registerAbstract(final AnnotatedType type, final Set<Type> abstractTypes,
      final BuildContext buildContext) {
    abstractTypes.addAll(collectAbstract(type, new HashSet<>(), buildContext));
  }

  @Override
  public Map<String, ?> convertOutput(final PropertiesMap original, final AnnotatedType type,
      final ResolutionEnvironment resolutionEnvironment) {
    return resolutionEnvironment.valueMapper.fromInput(original.asMap(), type.getType(), MAP);

  }

  @Override
  public boolean supports(final AnnotatedType type) {
    final Type actualType = type.getType();
    return actualType.equals(PropertiesMap.class);
  }

}
