package io.committed.vessel.server.data.services;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import io.committed.vessel.server.data.dataset.DataProviderSpecification;
import io.committed.vessel.server.data.dataset.Dataset;
import io.committed.vessel.server.data.providers.DataProvider;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class DatasetDataProviderCreationService implements BeanFactoryPostProcessor {

  private final DatasetRegistry corpusRegistry;
  private final DataProviderFactoryRegistry dataProviderFactoryRegistry;

  private final AtomicInteger nextId = new AtomicInteger();

  @Autowired
  public DatasetDataProviderCreationService(final DatasetRegistry corpusRegistry,
      final DataProviderFactoryRegistry dataProviderFactoryRegistry) {
    this.corpusRegistry = corpusRegistry;
    this.dataProviderFactoryRegistry = dataProviderFactoryRegistry;
  }

  @Override
  public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
    corpusRegistry.getCorpora()
        .doOnNext(c -> register(beanFactory, c))
        .subscribe();

  }

  private Dataset register(final ConfigurableListableBeanFactory beanFactory, final Dataset corpus) {
    corpus.getProviders().forEach(s -> register(beanFactory, corpus, s));
    return corpus;
  }

  private DataProvider register(final ConfigurableListableBeanFactory beanFactory,
      final Dataset corpus,
      final DataProviderSpecification spec) {

    final Mono<? extends DataProvider> mono =
        dataProviderFactoryRegistry.build(spec.getFactory(),
            corpus.getId(),
            spec.getSettings());

    final DataProvider provider = mono.block();

    if (provider != null) {
      beanFactory.registerSingleton(makeId(spec, corpus), provider);

    } else {
      log.error("Unable to create data provider from specifrication for corpus {}, {}",
          corpus.getId(), spec);
    }

    return provider;
  }

  private String makeId(final DataProviderSpecification spec, final Dataset corpus) {
    // Note we might have the same corpus having the same provider (eg as a way of federating over
    // mutiple datasets)
    // that's probably not questionable/unsupported but just in case we add a unique number to the
    // end of the bean name.
    return String.format("dp-%s-%s-%d", corpus.getId(), spec.getFactory(),
        nextId.incrementAndGet());
  }

}
