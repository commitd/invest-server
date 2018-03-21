package io.committed.invest.support.elasticsearch.utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.common.unit.TimeValue;
import io.netty.handler.timeout.TimeoutException;

public class StubListenableActionFuture<T> implements ListenableActionFuture<T> {
  private ActionListener<T> listener;
  private T triggerValue;
  private Exception triggerException;

  public StubListenableActionFuture() {
    // Default
  }

  public StubListenableActionFuture(final T value) {
    triggerValue = value;
  }

  @Override
  public T actionGet() {
    return null;
  }

  @Override
  public T actionGet(final String timeout) {
    return null;
  }

  @Override
  public T actionGet(final long timeoutMillis) {
    return null;
  }

  @Override
  public T actionGet(final long timeout, final TimeUnit unit) {
    return null;
  }

  @Override
  public T actionGet(final TimeValue timeout) {
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
  public T get() throws InterruptedException, ExecutionException {
    return null;
  }

  @Override
  public T get(final long timeout, final TimeUnit unit)
      throws InterruptedException, ExecutionException, TimeoutException {
    return null;
  }

  @Override
  public void addListener(final ActionListener<T> listener) {
    this.listener = listener;

    // If we've not already been triggered send that in
    if (this.triggerValue != null) {
      this.listener.onResponse(triggerValue);
    } else if (this.triggerException != null) {
      this.listener.onFailure(triggerException);
    }
  }

  public void triggerResponse(final T value) {
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
