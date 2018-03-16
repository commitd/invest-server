package io.committed.invest.server.graphql;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import io.committed.invest.server.graphql.data.GraphQlQuery;
import io.committed.invest.test.InvestTestContext;


@RunWith(SpringRunner.class)
@WebFluxTest
@ContextConfiguration(classes = {InvestTestContext.class})
@Import({GraphQlConfig.class})
@DirtiesContext
public class InvestGraphQlTest {


  @Autowired
  private WebTestClient webClient;

  @Test
  public void examplePostGraphQlWhenNoServices() {
    final GraphQlQuery query = new GraphQlQuery();
    query.setQuery("query { empty }");

    this.webClient.post()
        .uri("/graphql")
        .syncBody(query)
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .json("{\"data\":{\"empty\":\"Add some GraphQL service plugins\"}}");
  }

  @Test
  public void exampleGetGraphQlWhenNoServices() {


    this.webClient.get()
        .uri("/graphql?query={query}", "query { empty }")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .json("{\"data\":{\"empty\":\"Add some GraphQL service plugins\"}}");
  }

  @Test
  public void exampleGraphQlSchemaJson() {
    final EntityExchangeResult<String> result = this.webClient.get()
        .uri("/schema.json")
        .exchange()
        .expectStatus().isOk()
        .expectBody(String.class).returnResult();

    assertThat(result.getResponseBody()).isNotBlank();
  }
}
