package io.committed.invest.core.dto.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import com.fasterxml.jackson.annotation.JsonCreator;
import reactor.core.publisher.Flux;

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
