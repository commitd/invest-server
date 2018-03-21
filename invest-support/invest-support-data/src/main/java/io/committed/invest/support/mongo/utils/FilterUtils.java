package io.committed.invest.support.mongo.utils;

import java.util.List;
import java.util.Optional;
import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;

/**
 * Utiltity classes for Mongo Filters.
 */
public final class FilterUtils {

  private FilterUtils() {
    // Singleton
  }

  /**
   * Combine multiple filter into and query
   *
   * @param filters the filters
   * @return the optional
   */
  public static Optional<Bson> combine(final List<Bson> filters) {
    if (filters == null || filters.isEmpty()) {
      return Optional.empty();
    } else if (filters.size() == 1) {

      return Optional.ofNullable(filters.get(0));
    } else {

      return Optional.of(Filters.and(filters));
    }
  }
}
