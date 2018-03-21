package io.committed.invest.plugins.ui.host.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.util.InMemoryResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.WebSession;
import io.committed.invest.core.graphql.InvestRootContext;
import io.committed.invest.core.services.UiUrlService;
import io.committed.invest.extensions.InvestUiExtension;
import io.committed.invest.plugins.ui.host.data.InvestHostedUiExtensions;
import io.committed.invest.plugins.ui.host.data.PluginJson;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest
@ContextConfiguration(classes = {InvestRootContext.class, UiRouter.class})
public class UiRouterTest {

  @Autowired
  WebTestClient client;

  @Test
  public void test() {
    client.get().uri("/test/example/index.html")
        .exchange()
        .expectStatus().isOk()
        .expectBody(String.class).isEqualTo("example");


    // Just check also missing other plugins!
    client.get().uri("/test/missing/index.html")
        .exchange()
        .expectStatus().is4xxClientError();
  }

  @TestConfiguration
  public static class Configuration {

    @Bean
    public InvestHostedUiExtensions investHostedExtensions() {
      final PluginJson plugin = new PluginJson();
      plugin.setId("test-plugin");
      plugin.setResource(new SameResource("example"));
      return new InvestHostedUiExtensions(Arrays.asList(plugin));
    }

    @Bean
    public Mono<WebSession> webSession() {
      return Mono.empty();
    }

    @Bean
    public Mono<Authentication> authentication() {
      return Mono.empty();
    }


    // We return a UriUrlService which would normally we provided by the app
    @Bean
    public UiUrlService urlService() {
      return new UiUrlService() {

        @Override
        public boolean isPathForExtensionRoot(final String path) {
          return path.length() > "/test/example/".length();
        }

        @Override
        public String getContextRelativePath(final InvestUiExtension extension) {
          return "/example";
        }

        @Override
        public String getContextPath() {
          return "/test";
        }
      };
    }
  }

  /**
   * This is basically a resource wwhich weill return the same things whatever is asked of it!
   */
  public static class SameResource extends InMemoryResource {

    public SameResource(final String source) {
      super(source);
    }

    @Override
    public Resource createRelative(final String relativePath) throws IOException {
      return this;
    }

    @Override
    public URL getURL() throws IOException {
      return new URL("http://localhost/test/example/");
    }
  }
}
