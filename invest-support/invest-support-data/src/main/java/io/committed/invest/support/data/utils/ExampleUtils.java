package io.committed.invest.support.data.utils;

import org.springframework.data.domain.ExampleMatcher;

public class ExampleUtils {

  public static ExampleMatcher exampleMatcher() {
    // Spring by default adds in the _class in "MongoDocument" but since save the class via Spring
    // that field does not exist. So we ignore that field when creating the query.
    return ExampleMatcher.matching()
        .withIgnorePaths("_class");
  }

}
