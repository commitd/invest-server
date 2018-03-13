package io.committed.invest.extensions.data.dataset;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * A dataset registry holds all configured and available datasets for the application.
 *
 */
public interface DatasetRegistry {

  /**
   * Get all datasets
   *
   * @return
   */
  Flux<Dataset> getDatasets();

  /**
   * FInd a specific dataset by its id
   * 
   * @param id
   * @return
   */
  Mono<Dataset> findById(String id);

}
