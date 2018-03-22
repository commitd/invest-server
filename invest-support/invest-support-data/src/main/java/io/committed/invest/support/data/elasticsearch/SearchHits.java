package io.committed.invest.support.data.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchHits<E> {
  private long total;

  private Flux<E> results;

}
