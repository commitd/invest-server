package io.committed.invest.support.elasticsearch.utils;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ListenableActionFuture;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Helpers to convert from ES responses to Reactive types.
 */
@Slf4j
public class ReactiveElasticsearchUtils {

  private ReactiveElasticsearchUtils() {
    // Singleton
  }

  /**
   * Conert a ES future to a mono.
   *
   * On error the mono will be empty;
   *
   * @param <T> the generic type
   * @param future the future
   * @return the mono
   */
  @SuppressWarnings("unchecked")
  public static <T> Mono<T> toMono(final ListenableActionFuture<T> future) {
    // Not sure why this complains about the cast (its jus thtat onError is too generic)
    return (Mono<T>) Mono.create(sink -> {
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
    })
        .onErrorResume(e -> {
          log.warn("Error in ES future, ignoring...", e);
          return Mono.<T>empty();
        });
  }


}
