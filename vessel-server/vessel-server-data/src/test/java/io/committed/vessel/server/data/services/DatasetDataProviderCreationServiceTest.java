package io.committed.vessel.server.data.services;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import io.committed.vessel.server.data.dataset.DataProviderSpecification;
import io.committed.vessel.server.data.dataset.Dataset;
import io.committed.vessel.server.data.testing.AnotherFakeDataProviderFactory;
import io.committed.vessel.server.data.testing.FakeDataProvider;
import io.committed.vessel.server.data.testing.FakeDataProviderFactory;

@RunWith(SpringRunner.class)
public class DatasetDataProviderCreationServiceTest {


  DataProviderFactoryRegistry dpfr = new DataProviderFactoryRegistry(Arrays.asList(
      new FakeDataProviderFactory(),
      new AnotherFakeDataProviderFactory()));

  DatasetRegistry dr = new DatasetRegistry(Arrays.asList(
      Dataset.builder().id("testds")
          .providers(Arrays.asList(
              DataProviderSpecification.builder().factory(FakeDataProviderFactory.ID).build()))
          .build()));

  @MockBean
  ConfigurableListableBeanFactory beanFactory;

  @Test
  public void test() {
    final DatasetDataProviderCreationService service =
        new DatasetDataProviderCreationService(dr, dpfr);

    service.postProcessBeanFactory(beanFactory);

    // TODO: This is a very unsophicated test, but we have 1 corpus and 1 provider in that and it
    // should resolve via one factory
    verify(beanFactory, only()).registerSingleton(Mockito.anyString(),
        Mockito.any(FakeDataProvider.class));
  }

}
