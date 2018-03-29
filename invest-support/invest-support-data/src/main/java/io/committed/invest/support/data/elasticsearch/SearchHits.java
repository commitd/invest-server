package io.committed.invest.support.data.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

/**
 * Represents SearchHits from ES.
 *
 * <p>Required as it carries the results and total.
 *
 * @param <E> the result type
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchHits<E> {
  private long total;

  private Flux<E> results;
}
