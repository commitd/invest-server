package io.committed.invest.core.dto.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Graph {

  private static Graph EMPTY;

  static {
    Graph.EMPTY = new Graph();
    Graph.EMPTY.setEdges(Collections.emptyList());
    Graph.EMPTY.setNodes(Collections.emptyList());
  }

  public static Graph empty() {
    return Graph.EMPTY;
  }

  public static Graph singleton(final Node node) {
    return new Graph(Collections.singleton(node), Collections.emptyList());
  }

  @JsonProperty("nodes")
  private List<Node> nodes;

  @JsonProperty("edges")
  private List<Edge> edges;

  public Graph(final Collection<Node> nodes, final Collection<Edge> edges) {
    this.nodes = new ArrayList<>(nodes);
    this.edges = new ArrayList<>(edges);
  }
}
