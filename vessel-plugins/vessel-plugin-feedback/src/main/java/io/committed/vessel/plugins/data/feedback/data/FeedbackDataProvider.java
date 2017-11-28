package io.committed.vessel.plugins.data.feedback.data;

import io.committed.vessel.server.data.providers.DataProvider;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FeedbackDataProvider extends DataProvider {

  @Override
  default String getProviderType() {
    return "Feedback";
  }

  Flux<Feedback> findAll(int offset, int limit);

  Mono<Feedback> save(Feedback feedback);

  void delete(String id);
}
