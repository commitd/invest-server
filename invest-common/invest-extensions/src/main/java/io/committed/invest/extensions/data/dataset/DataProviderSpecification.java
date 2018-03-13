package io.committed.invest.extensions.data.dataset;

import java.util.Collections;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A data provider specification is a definition of a data provider to be created.
 *
 * It compromises of several components:
 *
 * <li>factory - The id of the Data Provider Factory which should be used to create the data
 * provider
 * <li>datasource - a name/id uniquely specifies the source of the data. This allows us to
 * distinguish where different data providers offer the access to the same underlying data.
 * <li>setings - a map which contains free form setttings. This are passed directly to the factory
 * so should provide the information need by the factory requested.
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataProviderSpecification {

  private String factory;

  private String datasource;

  @Builder.Default
  private Map<String, Object> settings = Collections.emptyMap();

}
