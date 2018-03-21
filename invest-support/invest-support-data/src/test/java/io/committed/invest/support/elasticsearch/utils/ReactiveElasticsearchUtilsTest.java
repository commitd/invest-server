package io.committed.invest.support.elasticsearch.utils;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import reactor.core.publisher.Mono;

public class ReactiveElasticsearchUtilsTest {



  @Test
  public void testSuccess() {

    final StubListenableActionFuture<String> future = new StubListenableActionFuture<>();
    final Mono<String> mono = ReactiveElasticsearchUtils.toMono(future);

    future.triggerResponse("hello");

    assertThat(mono.block()).isEqualTo("hello");

  }

  @Test
  public void testFailure() {

    final StubListenableActionFuture<String> future = new StubListenableActionFuture<>();
    final Mono<String> mono = ReactiveElasticsearchUtils.toMono(future);

    future.triggerError(new Exception());

    assertThat(mono.blockOptional()).isNotPresent();

  }

}
