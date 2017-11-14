
package io.committed.vesssel.plugins.data.mongo;

import io.committed.vessel.extensions.VesselDataExtension;

public class MongoPlugin implements VesselDataExtension {

  @Override
  public Class<?> getConfiguration() {
    return ReactiveMongoConfiguration.class;
  }
}
