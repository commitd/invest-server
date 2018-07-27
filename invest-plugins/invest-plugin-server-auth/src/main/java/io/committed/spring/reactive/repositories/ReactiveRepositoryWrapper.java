package io.committed.spring.reactive.repositories;

import org.reactivestreams.Publisher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Converts a non reactive repository to a reactive repository.
 *
 * <p>This does the wrap in a blocking way, in that that at the point of return the action has been
 * executed.
 *
 * @param <T> the type to save
 * @param <I> the id
 * @param <R> the repository
 */
public class ReactiveRepositoryWrapper<T, I, R extends CrudRepository<T, I>>
    implements ReactiveCrudRepository<T, I> {

  protected final R repo;

  public ReactiveRepositoryWrapper(final R repo) {
    this.repo = repo;
  }

  @Override
  public <S extends T> Mono<S> save(final S entity) {
    return Mono.justOrEmpty(repo.save(entity));
  }

  @Override
  public <S extends T> Flux<S> saveAll(final Iterable<S> entities) {
    return Flux.fromIterable(repo.saveAll(entities));
  }

  @Override
  public <S extends T> Flux<S> saveAll(final Publisher<S> entityStream) {
    return saveAll(Flux.from(entityStream).toIterable());
  }

  @Override
  public Mono<T> findById(final I id) {
    return Mono.justOrEmpty(repo.findById(id));
  }

  @Override
  public Mono<T> findById(final Publisher<I> id) {
    return findById(Mono.from(id).block());
  }

  @Override
  public Mono<Boolean> existsById(final I id) {
    return Mono.justOrEmpty(repo.existsById(id));
  }

  @Override
  public Mono<Boolean> existsById(final Publisher<I> id) {
    return existsById(Mono.from(id).block());
  }

  @Override
  public Flux<T> findAll() {
    return Flux.fromIterable(repo.findAll());
  }

  @Override
  public Flux<T> findAllById(final Iterable<I> ids) {
    return Flux.fromIterable(repo.findAllById(ids));
  }

  @Override
  public Flux<T> findAllById(final Publisher<I> idStream) {
    return findAllById(Flux.from(idStream).toIterable());
  }

  @Override
  public Mono<Long> count() {
    return Mono.just(repo.count());
  }

  @Override
  public Mono<Void> deleteById(final I id) {
    repo.deleteById(id);
    return Mono.empty();
  }

  @Override
  public Mono<Void> deleteById(final Publisher<I> id) {
    deleteById(Mono.from(id).block());
    return Mono.empty();
  }

  @Override
  public Mono<Void> delete(final T entity) {
    repo.delete(entity);
    return Mono.empty();
  }

  @Override
  public Mono<Void> deleteAll(final Iterable<? extends T> entities) {
    repo.deleteAll(entities);
    return Mono.empty();
  }

  @Override
  public Mono<Void> deleteAll(final Publisher<? extends T> entityStream) {
    return deleteAll(Flux.from(entityStream).toIterable());
  }

  @Override
  public Mono<Void> deleteAll() {
    repo.deleteAll();
    return Mono.empty();
  }
}
