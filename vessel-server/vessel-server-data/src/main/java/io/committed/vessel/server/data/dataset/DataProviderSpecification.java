package io.committed.vessel.server.data.dataset;

import java.util.Collections;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataProviderSpecification {

  // the dataproviderfactory id
  private String factory;

  // The a id which uniquely specifies the source of the data (could be the name of the baleen
  // pipeline + collection which generated the data)
  // Different data provides of the same or different data provider types might be fed by the same
  // data sources.
  private String datasource;

  private Map<String, Object> settings = Collections.emptyMap();

}
