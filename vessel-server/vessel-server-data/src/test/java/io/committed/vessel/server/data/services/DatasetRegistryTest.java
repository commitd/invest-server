package io.committed.vessel.server.data.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import io.committed.vessel.server.data.dataset.Dataset;
import io.committed.vessel.server.data.testing.Fixtures;


public class DatasetRegistryTest {

  @Test
  public void testGetCorpora() {


    final List<Dataset> datasets = Fixtures.createDatasets();


    final DatasetRegistry registry = new DatasetRegistry(datasets);

    assertThat(registry.getDatasets().collectList().block()).containsExactlyElementsOf(datasets);
  }

  @Test
  public void testFindById() {
    final List<Dataset> datasets = Fixtures.createDatasets();
    final DatasetRegistry registry = new DatasetRegistry(datasets);

    assertThat(registry.findById("a").block()).isEqualTo(datasets.get(0));
  }


  @Test
  public void testFindByIdMissing() {
    final List<Dataset> datasets = Fixtures.createDatasets();
    final DatasetRegistry registry = new DatasetRegistry(datasets);

    assertThat(registry.findById("missing").hasElement().block()).isFalse();
  }



}
