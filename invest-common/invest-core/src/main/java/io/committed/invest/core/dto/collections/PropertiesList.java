package io.committed.invest.core.dto.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * A list of properties.
 *
 * <p>In GraphQL is have some issues managing properties (String =&gt; Object) maps. Having created
 * {@link PropertiesMap} is seemed obvious to create a list equivalent. Whilst a regular list will
 * work we define our own list here which allows control of serialisations etc. It also provides a
 * single type for GraphQL extensions.
 *
 * <p>Developers should use this in GraphQL 'dtos' wherever they'd normally wish to use a List%lt;
 * Pair&lt;String,Object&gt; &gt; type of construct. Or more likely where they'd really want an
 * GraphQL to offer an ordered map as an array.
 */
public final class PropertiesList {

  private final List<Property> list = new LinkedList<>();

  public PropertiesList() {
    // Singleton
  }

  @JsonCreator
  public PropertiesList(final Collection<Property> list) {
    if (list != null) {
      list.forEach(this::add);
    }
  }

  public PropertiesList(final Map<String, Object> map) {
    if (map != null) {
      map.forEach((k, v) -> list.add(new Property(k, v)));
    }
  }

  public void add(final String key, final Object value) {
    list.add(new Property(key, value));
  }

  public void add(final Property property) {
    list.add(property);
  }

  public Stream<Property> stream() {
    return list.stream();
  }

  public Flux<Property> flux() {
    return Flux.fromIterable(list);
  }

  public boolean isEmpty() {
    return list.isEmpty();
  }

  public List<Property> asList() {
    return Collections.unmodifiableList(list);
  }

  @Override
  public int hashCode() {
    return Objects.hash(list);
  }

  @Override
  @SuppressWarnings("squid:S2583")
  public boolean equals(final Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    final PropertiesList other = (PropertiesList) obj;
    // DONT NOT REMOVE, suppresswarning
    // Sonar complains about this (the field IS final) but EqualsVerifier will actuall change it to
    // null
    // so if you remove this, you'll then have
    if (list == null) {
      if (other.list != null) return false;
    } else if (!list.equals(other.list)) return false;
    return true;
  }
}
