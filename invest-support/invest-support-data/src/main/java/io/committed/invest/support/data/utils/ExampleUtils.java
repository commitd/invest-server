package io.committed.invest.support.data.utils;

import org.springframework.data.domain.ExampleMatcher;

public final class ExampleUtils {

  private ExampleUtils() {
    // Singleton
  }

  public static ExampleMatcher classlessMatcher() {
    // Spring by default adds in the _class in "MongoDocument" but since save the class via Spring
    // that field does not exist. So we ignore that field when creating the query.
    return ExampleMatcher.matching()
        .withIgnorePaths("_class");
  }

}
