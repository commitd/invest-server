package io.committed.invest.extensions.data.providers;

/**
 * A base implementation of DataProviderFactory
 *
 * @param <P> the provider type.
 */
public abstract class AbstractDataProviderFactory<P extends DataProvider>
    implements DataProviderFactory<P> {

  private final String id;

  private final Class<P> dataProvider;

  private final String database;

  protected AbstractDataProviderFactory(final String id, final Class<P> clazz,
      final String database) {
    this.id = id;
    this.dataProvider = clazz;
    this.database = database;
  }


  @Override
  public String getId() {
    return id;
  }

  @Override
  public Class<P> getDataProvider() {
    return dataProvider;
  }

  @Override
  public String getDatabase() {
    return database;
  }


}
