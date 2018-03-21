package io.committed.spring.reactive.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.repository.CrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactiveRepositoryWrapperTest {

  private CrudRepository repo;
  private ReactiveRepositoryWrapper wrapper;

  private final Object entity = new Object();
  private final List<Object> entities = Arrays.asList(new Object(), new Object());
  private final List<Object> ids = Arrays.asList("123", "456", "789");

  @Before
  public void before() {
    repo = mock(CrudRepository.class);
    wrapper = new ReactiveRepositoryWrapper(repo);
  }

  @Test
  public void testSave() {
    wrapper.save(entity);
    verify(repo).save(entity);
  }

  @Test
  public void testSaveAllIterableOfS() {
    wrapper.saveAll(entities);
    verify(repo).saveAll(entities);
  }

  @Test
  public void testSaveAllPublisherOfS() {
    wrapper.saveAll(Flux.fromIterable(entities));

    final ArgumentCaptor<Iterable<Object>> captor = ArgumentCaptor.forClass(Iterable.class);
    verify(repo).saveAll(captor.capture());
    assertThat(captor.getValue()).containsAll(entities);
  }

  @Test
  public void testFindByIdI() {
    wrapper.findById(123);
    verify(repo).findById(123);
  }

  @Test
  public void testFindByIdPublisherOfI() {
    wrapper.findById(Mono.just(123));
    verify(repo).findById(123);
  }

  @Test
  public void testExistsByIdI() {
    wrapper.existsById("123");
    verify(repo).existsById("123");
  }

  @Test
  public void testExistsByIdPublisherOfI() {
    wrapper.existsById(Mono.just(123));
    verify(repo).existsById(123);
  }

  @Test
  public void testFindAll() {
    wrapper.findAll();
    verify(repo).findAll();
  }

  @Test
  public void testFindAllByIdIterableOfI() {
    wrapper.findAllById(ids);
    verify(repo).findAllById(ids);
  }

  @Test
  public void testFindAllByIdPublisherOfI() {
    wrapper.findAllById(Flux.fromIterable(ids));

    final ArgumentCaptor<Iterable<Object>> captor = ArgumentCaptor.forClass(Iterable.class);
    verify(repo).findAllById(captor.capture());
    assertThat(captor.getValue()).containsAll(ids);
  }

  @Test
  public void testCount() {
    wrapper.count();
    verify(repo).count();
  }

  @Test
  public void testDeleteByIdI() {
    wrapper.deleteById(123);
    verify(repo).deleteById(123);
  }

  @Test
  public void testDeleteByIdPublisherOfI() {
    wrapper.deleteById(Mono.just(123));
    verify(repo).deleteById(123);
  }

  @Test
  public void testDelete() {
    wrapper.delete(entity);
    verify(repo).delete(entity);
  }

  @Test
  public void testDeleteAllIterableOfQextendsT() {
    wrapper.deleteAll(entities);
    verify(repo).deleteAll(entities);
  }

  @Test
  public void testDeleteAllPublisherOfQextendsT() {
    wrapper.deleteAll(Flux.fromIterable(entities));

    final ArgumentCaptor<Iterable<Object>> captor = ArgumentCaptor.forClass(Iterable.class);
    verify(repo).deleteAll(captor.capture());
    assertThat(captor.getValue()).containsAll(entities);
  }

  @Test
  public void testDeleteAll() {
    wrapper.deleteAll();
    verify(repo).deleteAll();
  }

}
