package io.committed.invest.plugin.server.audit;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import io.committed.invest.plugin.server.audit.services.AuditService;
import io.committed.invest.test.InvestTestContext;
import lombok.Data;

@RunWith(SpringRunner.class)
@WebFluxTest
@ContextConfiguration(classes = {InvestTestContext.class})
@Import({ApiAuditConfig.class})
@DirtiesContext
public class ApiAuditTest {

  @Autowired
  private WebTestClient webClient;

  @Autowired
  private TestAuditService auditService;

  @Test
  public void testOk() {

    this.webClient.get()
        .uri("/")
        .exchange()
        .expectStatus().isOk()
        .expectBody(String.class).isEqualTo("Test");

    assertThat(auditService.getAction()).isEqualTo("ApiRes GET:/");

  }

  @Test
  public void testError() {

    this.webClient.get()
        .uri("/missing")
        .exchange()
        .expectStatus().isNotFound();

    assertThat(auditService.getAction()).isEqualTo("ApiError GET:/missing");

  }



  @TestConfiguration
  public static class TestAuditConfiguration {

    @Bean
    public TestAuditService testAuditService() {
      return new TestAuditService();
    }

    @Bean
    public RouterFunction<ServerResponse> router() {
      return RouterFunctions.route(RequestPredicates.GET("/"), server -> {
        return ServerResponse.ok().syncBody("Test");
      });
    }
  }

  @Data
  public static class TestAuditService implements AuditService {

    private String message;
    private String user;
    private String action;
    private Object params;

    @Override
    public void audit(final String user, final String action, final String message, final Object params) {
      this.user = user;
      this.action = action;
      this.message = message;
      this.params = params;
    }

    @Override
    public boolean isLogging() {
      return true;
    }
  }
}
