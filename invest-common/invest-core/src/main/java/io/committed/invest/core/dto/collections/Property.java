package io.committed.invest.core.dto.collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A key-value class.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Property {
  private String key;
  private Object value;
}
