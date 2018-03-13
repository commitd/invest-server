package io.committed.invest.core.dto.graph;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import lombok.Data;
import lombok.RequiredArgsConstructor;

public class GraphBuilderTest {

  @Test
  public void testEmpty() {
    final GraphBuilder builder = new GraphBuilder();

    final Graph build = builder.build();
    assertTrue(build.getEdges().isEmpty());
    assertTrue(build.getNodes().isEmpty());

  }

  @Test
  public void testSimple() {
    final GraphBuilder builder = new GraphBuilder();

    final StubNode a = new StubNode(0);
    final StubNode b = new StubNode(1);

    final Edge e = new DefaultEdge(a.getId(), b.getId(), "g", "t");

    builder.addNode(a);
    builder.addNode(b);
    builder.addEdge(e);

    final Graph build = builder.build();
    assertThat(build.getEdges()).containsExactly(e);
    assertThat(build.getNodes()).containsExactly(a, b);

  }


  @Data
  @RequiredArgsConstructor
  public class StubNode implements Node {

    private final long id;

    @Override
    public String getGroup() {
      return "g";
    }

  }
}
