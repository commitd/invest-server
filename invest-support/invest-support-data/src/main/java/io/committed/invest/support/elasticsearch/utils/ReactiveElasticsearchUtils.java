package io.committed.invest.support.elasticsearch.utils;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ListenableActionFuture;
import reactor.core.publisher.Mono;

public class ReactiveElasticsearchUtils {

  private ReactiveElasticsearchUtils() {
    // Singleton
  }

  public static <T> Mono<T> toMono(final ListenableActionFuture<T> future) {
    return Mono.create(sink -> {
      future.addListener(new ActionListener<T>() {

        @Override
        public void onResponse(final T r) {
          sink.success(r);
        }

        @Override
        public void onFailure(final Exception e) {
          sink.error(e);

        }
      });

      sink.onCancel(() -> future.cancel(true));
    });
  }


}
