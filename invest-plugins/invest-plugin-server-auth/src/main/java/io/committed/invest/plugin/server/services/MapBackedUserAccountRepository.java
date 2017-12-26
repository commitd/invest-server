package io.committed.invest.plugin.server.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.util.StringUtils;

import io.committed.invest.plugin.server.auth.dao.UserAccount;

public class MapBackedUserAccountRepository implements UnreactiveUserAccountRepository {

  private final Map<String, UserAccount> db = new ConcurrentHashMap<>();

  private final AtomicLong id = new AtomicLong();

  private String randomId() {
    // TODO: A better implementation required here which will generate something more random perhaps
    return Long.toString(id.incrementAndGet());
  }

  @Override
  public <S extends UserAccount> S save(final S entity) {
    if (StringUtils.isEmpty(entity.getId())) {
      entity.setId(randomId());
    }
    db.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public <S extends UserAccount> Iterable<S> saveAll(final Iterable<S> entities) {
    return StreamSupport
        .stream(entities.spliterator(), false)
        .map(this::save)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<UserAccount> findById(final String id) {
    return Optional.ofNullable(db.get(id));
  }

  @Override
  public boolean existsById(final String id) {
    return db.get(id) != null;
  }

  @Override
  public Iterable<UserAccount> findAll() {

    return Collections.unmodifiableCollection(db.values());
  }

  @Override
  public Iterable<UserAccount> findAllById(final Iterable<String> ids) {
    final List<UserAccount> accounts = new ArrayList<UserAccount>();
    for (final String id : ids) {
      final UserAccount userAccount = db.get(id);
      if (userAccount != null) {
        accounts.add(userAccount);
      }
    }
    return accounts;
  }

  @Override
  public long count() {
    return db.size();
  }

  @Override
  public void deleteById(final String id) {
    db.remove(id);
  }

  @Override
  public void delete(final UserAccount entity) {
    db.remove(entity.getId());
  }

  @Override
  public void deleteAll(final Iterable<? extends UserAccount> entities) {
    StreamSupport.stream(entities.spliterator(), false).forEach(this::delete);

  }

  @Override
  public void deleteAll() {
    db.clear();
  }

  @Override
  public void deleteByUsername(final String username) {
    if (username == null) {
      return;
    }

    final Iterator<Entry<String, UserAccount>> iterator = db.entrySet().iterator();
    while (iterator.hasNext()) {
      final Entry<String, UserAccount> next = iterator.next();
      if (username.equals(next.getValue().getUsername())) {
        iterator.remove();
      }
    }
  }

  @Override
  public Stream<UserAccount> findByAuthorities(final String authority) {
    return db.values().stream().filter(p -> {
      return p.getAuthorities() != null && p.getAuthorities().contains(authority);
    });
  }

  @Override
  public Optional<UserAccount> findByUsername(final String username) {
    return db.values().stream().filter(p -> p.getUsername().equals(username)).findAny();
  }

}
