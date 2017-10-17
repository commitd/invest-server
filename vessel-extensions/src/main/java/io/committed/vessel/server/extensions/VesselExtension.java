package io.committed.vessel.server.extensions;

public interface VesselExtension {

  default String getId() {
    return this.getClass().getSimpleName();
  }

  default String getName() {
    return getId();
  }

  default String getDescription() {
    return "";
  }

  default Class<?> getConfiguration() {
    return null;
  }

}
