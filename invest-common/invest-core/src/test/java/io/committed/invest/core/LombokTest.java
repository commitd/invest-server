package io.committed.invest.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collection;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.WebSession;

import reactor.core.publisher.Mono;

import io.committed.invest.core.auth.AuthenticationSettings;
import io.committed.invest.core.dto.analytic.GeoBin;
import io.committed.invest.core.dto.auth.InvestUser;
import io.committed.invest.core.dto.collections.Property;
import io.committed.invest.core.dto.graph.DefaultEdge;
import io.committed.invest.core.dto.graph.Edge;
import io.committed.invest.core.dto.graph.Graph;
import io.committed.invest.core.dto.graph.Node;
import io.committed.invest.core.dto.system.Message;
import io.committed.invest.core.graphql.InvestRootContext;
import io.committed.invest.test.LombokDataTestSupport;

public class LombokTest {

  @Test
  public void testLombok() {
    final LombokDataTestSupport mt = new LombokDataTestSupport();
    mt.testPackage(GeoBin.class);
    mt.testPackage(InvestUser.class);
    mt.testPackage(Property.class);
    mt.testClass(DefaultEdge.class);
    mt.testPackage(Message.class);
    mt.testPackage(InvestRootContext.class);
    mt.testPackage(AuthenticationSettings.class);
  }

  @Test
  public void testRootContext() {
    // INvestRoot is a @value so...

    final Mono<WebSession> session = Mono.just(mock(WebSession.class));
    final Mono<Authentication> auth = Mono.just(mock(Authentication.class));

    final InvestRootContext investRootContext = new InvestRootContext(session, auth);
    assertThat(investRootContext.getAuthentication()).isSameAs(auth);
    assertThat(investRootContext.getSession()).isSameAs(session);
    assertThat(investRootContext.toString()).isNotBlank();

    EqualsVerifier.forClass(InvestRootContext.class)
        .withRedefinedSuperclass()
        .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
        .verify();
  }

  @Test
  public void testGraph() {
    final Collection<Node> nodes = Arrays.asList(mock(Node.class));
    final Collection<Edge> edges = Arrays.asList(mock(Edge.class));
    ;
    final Graph graph = new Graph(nodes, edges);

    assertThat(graph.getNodes()).hasSameElementsAs(nodes);
    assertThat(graph.getEdges()).hasSameElementsAs(edges);
  }
}
