package io.committed.invest.core.dto.system;

import lombok.Data;

@Data
public class PageResponse {

  private int offset;

  private int limit;

  private long total;
}
