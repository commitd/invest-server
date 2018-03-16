package io.committed.invest.plugins.ui.graphql;

import static org.assertj.core.api.Assertions.assertThat;
import java.net.URL;
import org.junit.Test;

public class GraphiQlUiExtensionTest {

  @Test
  public void test() {
    final GraphiQlUiExtension extension = new GraphiQlUiExtension();

    // ensure that index.html exists as suggested

    final String url = extension.getStaticResourcePath() + "index.html";
    final URL stream =
        GraphiQlUiExtension.class.getResource(url);
    assertThat(stream).isNotNull();
  }

}
