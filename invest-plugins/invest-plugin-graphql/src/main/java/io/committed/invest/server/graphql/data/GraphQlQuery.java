package io.committed.invest.server.graphql.data;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Representation of a GraphQL query */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GraphQlQuery {

  private String query;

  private String operationName;

  private Map<String, Object> variables;
}
