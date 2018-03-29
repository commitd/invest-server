package io.committed.invest.server.core.filters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import io.committed.invest.core.services.UiUrlService;

public class AddIndexHtmlWebFilterTest {

  @Test
  public void testNoIndexHtmlWhenNotRoot() {
    final String path = "/example/test/static/something.jpg";
    final String filterForNewPath = filterForNewPath(path, false);

    assertThat(filterForNewPath).isEqualTo(path);
  }

  @Test
  public void testWhenRootItsAdded() {
    final String filterForNewPath = filterForNewPath("/example/test/", true);

    assertThat(filterForNewPath).isEqualTo("/example/test/index.html");
  }

  private String filterForNewPath(final String path, final boolean isRoot) {
    final UiUrlService service = mock(UiUrlService.class);
    doReturn(isRoot).when(service).isPathForExtensionRoot(ArgumentMatchers.any());
    final AddIndexHtmlWebFilter filter = new AddIndexHtmlWebFilter(service);

    final MockServerHttpRequest request = MockServerHttpRequest.get(path).build();
    final ServerWebExchange exchange = MockServerWebExchange.from(request);
    final WebFilterChain chain = mock(WebFilterChain.class);
    filter.filter(exchange, chain);

    final ArgumentCaptor<ServerWebExchange> captor =
        ArgumentCaptor.forClass(ServerWebExchange.class);
    verify(chain).filter(captor.capture());

    return captor.getValue().getRequest().getPath().value().toString();
  }
}
