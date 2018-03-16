package io.committed.invest.server.graphql;

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
import io.committed.invest.extensions.annotations.GraphQLService;
import io.committed.invest.server.graphql.data.GraphQlQuery;
import io.committed.invest.test.InvestTestContext;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;


@RunWith(SpringRunner.class)
@WebFluxTest
@ContextConfiguration(classes = {InvestTestContext.class})
@Import({GraphQlConfig.class})
@DirtiesContext
public class InvestGraphQlWithServiceTest {


  @Autowired
  private WebTestClient webClient;



  @Test
  public void examplePostGraphQlWhenNoServices() {
    final GraphQlQuery query = new GraphQlQuery();
    query.setQuery("query { test(message:\"world\") }");

    this.webClient.post()
        .uri("/graphql")
        .syncBody(query)
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .json("{\"data\":{\"test\":\"Hello world\"}}");
  }

  @TestConfiguration
  static class StubServiceConfiguration {

    @Bean
    public StubResolver stubResolver() {
      return new StubResolver();
    }
  }

  @GraphQLService
  public static class StubResolver {

    @GraphQLQuery(name = "test")
    public String test(@GraphQLArgument(name = "message") final String msg) {
      return String.format("Hello %s", msg);
    }
  }


}
