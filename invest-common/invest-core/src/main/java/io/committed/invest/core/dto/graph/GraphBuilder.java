package io.committed.invest.core.dto.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/** Helper to build a {@link Graph} from nodes and edges. */
public class GraphBuilder {

  private final Map<Long, Node> nodes = new HashMap<>();
  private final Map<Long, Edge> idEdges = new HashMap<>();
  private final Collection<Edge> edges = new ArrayList<>();

  public void addEdge(final Edge edge) {
    if (edge.getId() != 0) {
      idEdges.putIfAbsent(edge.getId(), edge);
    } else {
      edges.add(edge);
    }
  }

  public void addNode(final Node node) {
    nodes.putIfAbsent(node.getId(), node);
  }

  public Graph build() {
    edges.addAll(idEdges.values());
    return new Graph(nodes.values(), edges);
  }

  public Collection<Node> getNodes() {
    return nodes.values();
  }
}
