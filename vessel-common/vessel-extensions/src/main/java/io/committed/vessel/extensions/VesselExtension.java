package io.committed.vessel.extensions;

import org.springframework.stereotype.Service;

@Service
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

}
