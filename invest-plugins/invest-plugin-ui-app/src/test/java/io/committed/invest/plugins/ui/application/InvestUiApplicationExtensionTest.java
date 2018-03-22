package io.committed.invest.plugins.ui.application;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import io.committed.invest.test.InvestTestContext;


@RunWith(SpringRunner.class)
@WebFluxTest
@ContextConfiguration(classes = {InvestTestContext.class, InvestUiApplicationExtension.class})
@DirtiesContext
public class InvestUiApplicationExtensionTest {

  @Autowired
  private WebTestClient webClient;

  @Test
  public void redirectFromRoot() {
    this.webClient.get()
        .uri("/")
        .exchange()
        .expectStatus().is3xxRedirection();
  }


  // TODO: Ideally we'd check this works, but there's no data there (its gitignored)
  @Test
  @Ignore
  public void getIndex() {
    this.webClient.get()
        .uri("/ui/app/index.html")
        .exchange()
        .expectStatus().is2xxSuccessful();
  }

}
