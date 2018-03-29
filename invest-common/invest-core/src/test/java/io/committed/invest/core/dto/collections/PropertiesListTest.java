package io.committed.invest.core.dto.collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import reactor.core.publisher.Mono;

public class PropertiesListTest {

  @Test
  public void testAdd() {
    final PropertiesList pl = new PropertiesList();

    assertTrue(pl.isEmpty());

    final Property p = new Property("test", "value");
    pl.add("test", "value");

    assertFalse(pl.isEmpty());
    final List<Property> javaMap = pl.asList();

    assertThat(javaMap).containsExactly(p);
  }

  @Test
  public void testAddWithSameName() {
    final PropertiesList pl = new PropertiesList();

    final Property p1 = new Property("test", "value");
    final Property p2 = new Property("test", "value2");

    pl.add(p1);
    pl.add(p2);

    final List<Property> javaMap = pl.asList();

    assertThat(javaMap).containsExactly(p1, p2);
    assertThat(javaMap).hasSize(2);
  }

  @Test
  public void testFlux() {
    final PropertiesList pl = new PropertiesList();

    pl.add(new Property("test1", "value1"));
    pl.add(new Property("test2", "value2"));

    final Mono<List<Property>> flux = pl.flux().collectList();

    final List<Property> java = pl.asList();

    assertThat(java).isEqualTo(flux.block());
  }

  @Test
  public void testStream() {
    final PropertiesList pl = new PropertiesList();

    pl.add(new Property("test1", "value1"));
    pl.add(new Property("test2", "value2"));

    final List<Property> streamList = pl.stream().collect(Collectors.toList());

    final List<Property> java = pl.asList();

    assertThat(java).isEqualTo(streamList);
  }

  @Test
  public void testFromList() {

    final Collection<Property> list = new ArrayList<>();
    final Property p = new Property("a", "b");
    list.add(p);

    final PropertiesList pl = new PropertiesList(list);

    assertThat(pl.asList()).containsExactly(p);
  }

  @Test
  public void testFromMap() {

    final Map<String, Object> m = new HashMap<>();
    m.put("a", "d");

    final PropertiesList pl = new PropertiesList(m);

    assertThat(pl.asList()).containsExactly(new Property("a", "d"));
  }
}
