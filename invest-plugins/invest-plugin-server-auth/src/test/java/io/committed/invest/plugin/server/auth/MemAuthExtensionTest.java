package io.committed.invest.plugin.server.auth;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import io.committed.invest.core.services.UiUrlService;
import io.committed.invest.server.graphql.GraphQlConfig;
import io.committed.invest.server.graphql.data.GraphQlQuery;
import io.committed.invest.test.InvestTestContext;
import lombok.Data;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AuthExtension.class, InvestTestContext.class, GraphQlConfig.class})
@ActiveProfiles("auth-mem")
@WebFluxTest
public class MemAuthExtensionTest {

  @Autowired
  AuthExtension extension;

  @MockBean
  UiUrlService urlService;

  @Autowired
  WebTestClient client;

  @Test
  public void test() {
    assertThat(extension).isNotNull();

    assertThat(extension.getName()).isNotBlank();
    assertThat(extension.getDescription()).isNotBlank();
    assertThat(extension.getId()).isNotBlank();
  }

  @Test
  public void testLoginLogoutMutationsSuccess() {

    final LoginResponse response = client.post().uri("/graphql")
        .syncBody(GraphQlQuery.builder().query("mutation { login(username:\"user\", password:\"user\") { session } }")
            .build())
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectBody(LoginResponse.class).returnResult().getResponseBody();

    assertThat(response.data.login.session).isNotBlank();

    // TODO more here
  }


  @Data
  public static class LoginResponse {
    private LoginData data;
  }

  @Data
  public static class LoginData {
    private Login login;
  }


  @Data
  public static class Login {
    private String session;
  }

}
