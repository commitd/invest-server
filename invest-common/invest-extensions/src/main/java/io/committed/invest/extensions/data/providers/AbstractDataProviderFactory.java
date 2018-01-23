package io.committed.invest.extensions.data.providers;

import lombok.Data;

@Data
public abstract class AbstractDataProviderFactory<P extends DataProvider>
    implements DataProviderFactory<P> {

  private String id;

  private final Class<P> dataProvider;

  private final String database;

  protected AbstractDataProviderFactory(final String id, final Class<P> clazz,
      final String database) {
    this.id = id;
    this.dataProvider = clazz;
    this.database = database;
  }


}
