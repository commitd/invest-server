package io.committed.invest.core.dto.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import com.fasterxml.jackson.annotation.JsonCreator;
import reactor.core.publisher.Flux;

/**
 * A map of string-value properties.
 *
 * GraphQL has no support for maps, and typically a map like object will get convented to a list of
 * key, value. In the value of generic object value its very difficult for GraphQL to know what to
 * do with the object. it becomes an ObjectScalar (raw JSON). This works for querying but not for
 * input.
 *
 * We introduce here a PropertiesMap which we manage the serialisation for as a dedicated scalar. It
 * is really a LinkedHashMap which mirrors the map-like / list-like constructs we'd expect in Java /
 * GraphQL representations.
 *
 * Developers should use this in GraphQL 'dtos' wherever they'd normally wish to use a
 * Map<String,Object>.
 *
 */
public class PropertiesMap {

  private final Map<String, Object> map = new LinkedHashMap<>();

  public PropertiesMap() {
    // Do nothing
  }

  public PropertiesMap(final Collection<Property> list) {
    if (list != null) {
      list.forEach(this::add);
    }
  }

  @JsonCreator
  public PropertiesMap(final Map<String, Object> map) {
    if (map != null) {
      this.map.putAll(map);
    }
  }

  public void add(final String key, final Object value) {
    map.put(key, value);
  }

  public void add(final Property property) {
    map.put(property.getKey(), property.getValue());
  }

  public Stream<Map.Entry<String, Object>> stream() {
    return map.entrySet().stream();
  }

  public Flux<Map.Entry<String, Object>> flux() {
    return Flux.fromIterable(map.entrySet());
  }

  public boolean isEmpty() {
    return map.isEmpty();
  }

  public Map<String, Object> asMap() {
    return Collections.unmodifiableMap(map);
  }

}
