package io.committed.invest.core.dto.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Graph {

  private static final Graph EMPTY = new Graph(Collections.emptyList(), Collections.emptyList());

  public static Graph empty() {
    return Graph.EMPTY;
  }

  public static Graph singleton(final Node node) {
    return new Graph(Collections.singleton(node), Collections.emptyList());
  }

  @JsonProperty("nodes")
  private final List<Node> nodes;

  @JsonProperty("edges")
  private final List<Edge> edges;

  public Graph(final Collection<Node> nodes, final Collection<Edge> edges) {
    this.nodes = Collections.unmodifiableList(new ArrayList<>(nodes));
    this.edges = Collections.unmodifiableList(new ArrayList<>(edges));
  }
}
