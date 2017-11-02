package io.committed.vessel.server.data.dataset;

import java.util.Collections;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataProviderSpecification {

  // the dataproviderfactory id
  private String factory;

  private Map<String, Object> settings = Collections.emptyMap();

}
