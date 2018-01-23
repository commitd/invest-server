package io.committed.invest.server.data.services;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import io.committed.invest.extensions.data.dataset.DataProviderSpecification;
import io.committed.invest.extensions.data.dataset.Dataset;
import io.committed.invest.extensions.data.providers.DataProvider;
import io.committed.invest.server.data.testing.AnotherFakeDataProviderFactory;
import io.committed.invest.server.data.testing.FakeDataProvider;
import io.committed.invest.server.data.testing.FakeDataProviderFactory;
import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
public class DatasetDataProviderCreationServiceTest {


  DataProviderFactoryRegistry dpfr = new DataProviderFactoryRegistry(
      Arrays.asList(new FakeDataProviderFactory(), new AnotherFakeDataProviderFactory()));

  DatasetRegistry dr = new DatasetRegistry(Arrays.asList(Dataset.builder().id("testds")
      .providers(Arrays
          .asList(DataProviderSpecification.builder().factory(FakeDataProviderFactory.ID).build()))
      .build()));

  @Test
  public void test() {
    final DataProviderCreator creator = new DataProviderCreator(dpfr);

    final Flux<DataProvider> providers = creator.createProviders(dr);

    final List<DataProvider> list = providers.collect(Collectors.toList()).block();

    // TODO: This is a very unsophicated test, but we have 1 corpus and 1 provider in that and it
    // should resolve via one factory
    assertThat(list).hasSize(1);
    assertThat(list.get(0)).isInstanceOf(FakeDataProvider.class);
  }

}
