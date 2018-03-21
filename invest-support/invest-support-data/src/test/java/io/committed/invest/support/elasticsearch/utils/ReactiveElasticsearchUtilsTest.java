package io.committed.invest.support.elasticsearch.utils;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.Test;
import reactor.core.publisher.Mono;

public class ReactiveElasticsearchUtilsTest {

  private final class ListenableActionFutureImplementation implements ListenableActionFuture<String> {
    private ActionListener<String> listener;
    private String triggerValue;
    private Exception triggerException;

    @Override
    public String actionGet() {
      return null;
    }

    @Override
    public String actionGet(final String timeout) {
      return null;
    }

    @Override
    public String actionGet(final long timeoutMillis) {
      return null;
    }

    @Override
    public String actionGet(final long timeout, final TimeUnit unit) {
      return null;
    }

    @Override
    public String actionGet(final TimeValue timeout) {
      return null;
    }

    @Override
    public boolean cancel(final boolean mayInterruptIfRunning) {
      return false;
    }

    @Override
    public boolean isCancelled() {
      return false;
    }

    @Override
    public boolean isDone() {
      return false;
    }

    @Override
    public String get() throws InterruptedException, ExecutionException {
      return null;
    }

    @Override
    public String get(final long timeout, final TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException {
      return null;
    }

    @Override
    public void addListener(final ActionListener<String> listener) {
      this.listener = listener;

      // If we've not already been triggered send that in
      if (this.triggerValue != null) {
        this.listener.onResponse(triggerValue);
      } else if (this.triggerException != null) {
        this.listener.onFailure(triggerException);
      }
    }

    public void triggerResponse(final String value) {
      if (this.listener != null) {
        this.listener.onResponse(value);
      } else {
        triggerValue = value;
      }
    }

    public void triggerError(final Exception e) {
      if (this.listener != null) {
        this.listener.onFailure(e);
      } else {
        triggerException = e;
      }
    }
  }

  @Test
  public void testSuccess() {

    final ListenableActionFutureImplementation future = new ListenableActionFutureImplementation();
    final Mono<String> mono = ReactiveElasticsearchUtils.toMono(future);

    future.triggerResponse("hello");

    assertThat(mono.block()).isEqualTo("hello");

  }

  @Test
  public void testFailure() {

    final ListenableActionFutureImplementation future = new ListenableActionFutureImplementation();
    final Mono<String> mono = ReactiveElasticsearchUtils.toMono(future);

    future.triggerError(new Exception());

    assertThat(mono.blockOptional()).isNotPresent();

  }

}
