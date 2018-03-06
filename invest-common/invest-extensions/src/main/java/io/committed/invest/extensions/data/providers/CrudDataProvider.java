package io.committed.invest.extensions.data.providers;

public interface CrudDataProvider<R, T> extends DataProvider {

  boolean delete(R reference);

  boolean save(T item);
}
