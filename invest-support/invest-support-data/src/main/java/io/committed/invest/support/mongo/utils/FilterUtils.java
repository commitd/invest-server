package io.committed.invest.support.mongo.utils;

import java.util.List;
import java.util.Optional;
import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;

public final class FilterUtils {

  private FilterUtils() {
    // Singleton
  }

  public static Optional<Bson> combine(final List<Bson> filters) {
    if (filters.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(Filters.and(filters));
    }
  }
}
