package io.committed.invest.server.graphql.mappers;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import graphql.schema.GraphQLScalarType;

import io.committed.invest.core.dto.collections.PropertiesList;
import io.committed.invest.core.dto.collections.Property;

import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.generator.BuildContext;
import io.leangen.graphql.generator.OperationMapper;
import io.leangen.graphql.generator.mapping.OutputConverter;
import io.leangen.graphql.generator.mapping.common.CachingMapper;
import io.leangen.graphql.util.Scalars;

/**
 * A dedicated mapper to convert {@link PropertiesList} from/to a scalar.
 *
 * <p>This is necessary because of the Object values in the Properties. Allowing SPQR to 'ignore'
 * the content of the PropertiesList and pass it through verbatim as JSON matches the intend of
 * PropertiesList/Map which is a catch all for 'stuff' we don't.
 *
 * <p>Based on SPQR's from ObjectScalarAdaptor
 */
public class PropertiesListScalarAdapter extends CachingMapper<GraphQLScalarType, GraphQLScalarType>
    implements OutputConverter<PropertiesList, List<Property>> {

  private static final AnnotatedType LIST = GenericTypeReflector.annotate(LinkedList.class);

  @Override
  public List<Property> convertOutput(
      final PropertiesList original,
      final AnnotatedType type,
      final ResolutionEnvironment resolutionEnvironment) {
    return resolutionEnvironment.valueMapper.fromInput(original.asList(), type.getType(), LIST);
  }

  @Override
  public boolean supports(final AnnotatedType type) {
    final Type actualType = type.getType();
    return actualType.equals(PropertiesList.class);
  }

  @Override
  protected GraphQLScalarType toGraphQLType(
      final String typeName,
      final AnnotatedType javaType,
      final OperationMapper operationMapper,
      final BuildContext buildContext) {
    return Scalars.graphQLObjectScalar(typeName);
  }

  @Override
  protected GraphQLScalarType toGraphQLInputType(
      final String typeName,
      final AnnotatedType javaType,
      final OperationMapper operationMapper,
      final BuildContext buildContext) {
    return toGraphQLType(typeName, javaType, operationMapper, buildContext);
  }
}
