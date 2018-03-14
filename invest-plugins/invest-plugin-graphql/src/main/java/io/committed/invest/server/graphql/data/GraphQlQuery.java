package io.committed.invest.server.graphql.data;

import java.util.Map;
import lombok.Data;

/**
 * Representation of a GraphQL query
 *
 */
@Data
public class GraphQlQuery {

  private String query;

  private String operationName;

  private Map<String, Object> variables;

}
