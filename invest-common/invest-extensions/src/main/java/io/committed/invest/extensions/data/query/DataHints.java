package io.committed.invest.extensions.data.query;

import java.util.Collection;
import io.committed.invest.extensions.data.providers.DataProvider;
import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;


@Data
public class DataHints {

  public static final DataHints DEFAULT = new DataHints(null, null, false);

  /**
   * Hint that we want to use a specific database type for this query.
   *
   * All other databases will be discarded (even if this database is not available)
   */
  private final String database;

  /**
   * Hint that we'd like to limit the this function to just this specific data source.
   *
   * All other datasources will be discarded (even if this one is not present).
   */
  private final String datasource;

  /**
   * If we have multiple providers for the same datasource should we use them? Typically you wouldn't
   * want this, because they would contain the same data so the query would duplicate the results.
   */
  private final boolean duplicate;

  public <T extends DataProvider> Flux<T> filter(final Flux<T> input) {

    Flux<T> flux = input;

    if (datasource != null) {
      flux = flux.filter(p -> datasource.equals(p.getDatasource()));
    }

    if (database != null) {

      flux = flux.filter(p -> database.equals(p.getDatabase()));
    }

    if (!duplicate) {
      flux = flux.groupBy(DataProvider::getDatasource).flatMap(GroupedFlux::next);
    }

    return flux;

  }

  public Flux<DataProvider> filter(final Collection<DataProvider> input) {
    return this.filter(Flux.fromIterable(input));
  }


}