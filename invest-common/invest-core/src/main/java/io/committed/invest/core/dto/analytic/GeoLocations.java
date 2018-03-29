package io.committed.invest.core.dto.analytic;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Collection of geolocations
 *
 * @param <T> subtype of location
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocations<T extends GeoLocation> {
  @JsonProperty("locations")
  private Collection<T> locations;
}
