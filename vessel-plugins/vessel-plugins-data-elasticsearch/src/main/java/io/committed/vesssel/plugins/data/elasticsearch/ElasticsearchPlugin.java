package io.committed.vesssel.plugins.data.elasticsearch;

import io.committed.vessel.extensions.VesselDataExtension;

public class ElasticsearchPlugin implements VesselDataExtension {

  @Override
  public Class<?> getConfiguration() {
    return ElasticsearchConfiguration.class;
  }
}
