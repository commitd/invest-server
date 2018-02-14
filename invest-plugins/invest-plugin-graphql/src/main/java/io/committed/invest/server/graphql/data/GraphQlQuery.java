package io.committed.invest.server.graphql.data;

import java.util.Map;
import lombok.Data;

@Data
public class GraphQlQuery {

  private String query;

  private String operationName;

  private Map<String, Object> variables;

}
