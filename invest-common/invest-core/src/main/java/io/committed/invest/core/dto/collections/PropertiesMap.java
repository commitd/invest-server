package io.committed.invest.core.dto.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * A map of string-value properties.
 *
 * <p>GraphQL has no support for maps, and typically a map like object will get convented to a list
 * of key, value. In the value of generic object value its very difficult for GraphQL to know what
 * to do with the object. it becomes an ObjectScalar (raw JSON). This works for querying but not for
 * input.
 *
 * <p>We introduce here a PropertiesMap which we manage the serialisation for as a dedicated scalar.
 * It is really a LinkedHashMap which mirrors the map-like / list-like constructs we'd expect in
 * Java / GraphQL representations.
 *
 * <p>Developers should use this in GraphQL 'dtos' wherever they'd normally wish to use a
 * Map&lt;String,Object&gt;.
 */
public final class PropertiesMap {

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

  @Override
  public int hashCode() {
    return Objects.hash(map);
  }

  @Override
  @SuppressWarnings("squid:S2583")
  public boolean equals(final Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    final PropertiesMap other = (PropertiesMap) obj;
    if (map == null) {
      if (other.map != null) return false;
    } else if (!map.equals(other.map)) return false;
    return true;
  }

  /**
   * Gets value of key if present and it the same right as default
   *
   * @param <T> the generic type
   * @param key the key
   * @param defaultValue (do not use null!_
   * @return the t
   */
  @SuppressWarnings("unchecked")
  public <T> T get(final String key, final T defaultValue) {
    final Class<T> clazz = (Class<T>) defaultValue.getClass();

    final Object value = map.get(key);
    if (value == null || !clazz.isInstance(value)) {
      return defaultValue;
    } else {
      return (T) value;
    }
  }
}
