package io.committed.invest.support.data.mongo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

public class AbstractSpringDataMongoRepositoryDataProviderTest {

  @Test
  public void test() {
    final ReactiveCrudRepository<Object, String> repo = mock(ReactiveCrudRepository.class);
    final ReactiveMongoTemplate mongo = mock(ReactiveMongoTemplate.class);

    final AbstractSpringDataMongoRepositoryDataProvider<
            Object, String, ReactiveCrudRepository<Object, String>>
        dp =
            new AbstractSpringDataMongoRepositoryDataProvider(
                "dataset", "datasource", mongo, repo) {

              @Override
              public String getProviderType() {
                return null;
              }
            };

    assertThat(dp.getRepository()).isEqualTo(repo);

    doReturn(Mono.just(30L)).when(repo).count();
    assertThat(dp.count().block()).isEqualTo(30L);
  }
}
