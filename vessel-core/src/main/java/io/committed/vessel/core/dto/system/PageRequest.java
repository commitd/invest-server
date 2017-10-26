package io.committed.vessel.core.dto.system;

import lombok.Data;

@Data
public class PageRequest {

  private int offset;

  private int limit;
}
