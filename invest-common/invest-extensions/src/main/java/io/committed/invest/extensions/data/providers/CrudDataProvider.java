package io.committed.invest.extensions.data.providers;

/**
 * A Data Provider which supports crud operates.
 *
 * The is a simple inteface but worth noting is the two parameters R, T.
 *
 * It is certain that when you need to save an item you will require the full item (the value T).
 *
 * However when you delete an item you typically only require enough information to reference it
 * (the value R) uniquely.
 *
 * In many cases R is a subset of T or R might simply be a database id.
 *
 * @param <R> the type to use to delete
 * @param <T> the type to save
 */
public interface CrudDataProvider<R, T> extends DataProvider {

  /**
   * Delete the reference.
   *
   * @param reference the reference
   * @return true, if successful (typically that means that something has been deleted from the
   *         dataprovider)
   */
  boolean delete(R reference);

  /**
   * Create, save or update an item.
   *
   * @param item the item
   * @return true, if successful(typically that means that something has been deleted from the
   *         dataprovider)
   */
  boolean save(T item);
}
