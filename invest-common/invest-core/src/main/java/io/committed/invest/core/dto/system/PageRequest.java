/*
 *
 */
package io.committed.invest.core.dto.system;

import lombok.Data;

/**
 * A request for a specific page of results.
 */
@Data
public class PageRequest {

  /** The offset in the results to start at. */
  private int offset;

  /**
   * Number of results to return
   */
  private int size;
}
