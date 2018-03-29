package io.committed.invest.extensions.data.query;

import lombok.Data;
import reactor.core.publisher.Flux;

import io.committed.invest.extensions.data.providers.DataProvider;
import io.committed.invest.extensions.data.providers.DataProviders;

/**
 * Hints at which {@link DataProvider} should be used in a query/mutation.
 *
 * <p>Invest largely treats any two DataProvider's which implement the same DataProvider interface
 * as the same. However there are likely to be some differences. For example, one database type
 * might be fast to execute a query. Alternatively the user might want to limit to a specific source
 * of data.
 *
 * <p>This hints object acts as a generic filter for the {@link DataProviders} registry.
 */
@Data
public class DataHints {

  public static final DataHints DEFAULT = new DataHints(null, null, false);

  /**
   * Hint that we want to use a specific database type for this query.
   *
   * <p>All other databases will be discarded (even if this database is not available)
   */
  private final String database;

  /**
   * Hint that we'd like to limit the this function to just this specific data source.
   *
   * <p>All other datasources will be discarded (even if this one is not present).
   */
  private final String datasource;

  /**
   * If we have multiple providers for the same datasource should we use them? Typically you
   * wouldn't want this, because they would contain the same data so the query would duplicate the
   * results.
   */
  private final boolean duplicate;

  /**
   * Filters a flux of data providers based on these hints.
   *
   * @param <T> the generic type
   * @param input the input
   * @return the flux
   */
  public <T extends DataProvider> Flux<T> filter(final Flux<T> input) {

    Flux<T> flux = input;

    if (datasource != null) {
      flux = flux.filter(p -> datasource.equals(p.getDatasource()));
    }

    if (database != null) {
      flux = flux.filter(p -> database.equals(p.getDatabase()));
    }

    if (!duplicate) {

      // I have only managed to make this work with reduce, I guess because we need to consume the
      // flux
      // for the groupBy to work?
      flux =
          flux.groupBy(dp -> String.format("%s#%s", dp.getDatasource(), dp.getProviderType()))
              .flatMap(f -> f.reduce((a, b) -> a));
    }

    return flux;
  }
}
