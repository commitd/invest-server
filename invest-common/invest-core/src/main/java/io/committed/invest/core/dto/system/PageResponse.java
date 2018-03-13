package io.committed.invest.core.dto.system;

import lombok.Data;

@Data
public class PageResponse {

  /**
   * Offset of first result
   */
  private int offset;

  /**
   * Number of results requested
   */
  private int size;

  /**
   * Number of results which match the query
   */
  private long total;
}
