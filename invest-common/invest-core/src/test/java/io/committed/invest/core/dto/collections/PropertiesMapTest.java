package io.committed.invest.core.dto.collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.junit.Test;
import reactor.core.publisher.Mono;

public class PropertiesMapTest {

  @Test
  public void testAdd() {
    final PropertiesMap map = new PropertiesMap();

    assertTrue(map.isEmpty());

    map.add(new Property("test", "value"));

    assertFalse(map.isEmpty());
    final Map<String, Object> javaMap = map.asMap();

    assertThat(javaMap).containsEntry("test", "value");
    assertThat(javaMap).hasSize(1);
  }

  @Test
  public void testReplace() {
    final PropertiesMap map = new PropertiesMap();


    map.add(new Property("test", "value"));
    map.add(new Property("test", "value2"));

    final Map<String, Object> javaMap = map.asMap();

    assertThat(javaMap).containsEntry("test", "value2");
    assertThat(javaMap).hasSize(1);
  }

  @Test
  public void testFlux() {
    final PropertiesMap map = new PropertiesMap();

    map.add(new Property("test1", "value1"));
    map.add(new Property("test2", "value2"));

    final Mono<Map<String, Object>> fluxMap = map.flux().collectMap(Entry::getKey, Entry::getValue);

    final Map<String, Object> javaMap = map.asMap();

    assertThat(javaMap).isEqualTo(fluxMap.block());
  }

  @Test
  public void testStream() {
    final PropertiesMap map = new PropertiesMap();

    map.add(new Property("test1", "value1"));
    map.add(new Property("test2", "value2"));

    final Map<String, Object> streamMap = map.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue));

    final Map<String, Object> javaMap = map.asMap();

    assertThat(javaMap).isEqualTo(streamMap);
  }

  @Test
  public void testFromList() {

    final Collection<Property> list = new ArrayList<>();
    list.add(new Property("a", "b"));

    final PropertiesMap map = new PropertiesMap(list);

    assertThat(map.asMap()).containsEntry("a", "b");
  }

  @Test
  public void testFromMap() {

    final Map<String, Object> m = new HashMap<>();
    m.put("a", "d");

    final PropertiesMap map = new PropertiesMap(m);

    assertThat(map.asMap()).containsEntry("a", "d");
  }

  @Test
  public void testGet() {
    final Map<String, Object> m = new HashMap<>();
    m.put("a", "d");
    final PropertiesMap map = new PropertiesMap(m);


    assertThat(map.get("a", "e")).isEqualTo("d");
    assertThat(map.get("b", "e")).isEqualTo("e");
    assertThat(map.get("a", 123)).isEqualTo(123);


  }
}
