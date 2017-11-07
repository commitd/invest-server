package io.committed.vessel.core.dto.analytic;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocations<T extends GeoLocation> {
  @JsonProperty("locations")
  private Collection<T> locations;

}
