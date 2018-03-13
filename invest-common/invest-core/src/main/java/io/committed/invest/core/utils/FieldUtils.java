package io.committed.invest.core.utils;

import java.util.Collections;
import java.util.List;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * Helpers for processing paths and fiels within properties and databases.
 *
 * The path segments are separated by ".".
 *
 */
public final class FieldUtils {
  private static final Joiner FIELD_JOINER = Joiner.on(".").skipNulls();

  // Fixed limit here to avoid some crazy depth, when in reality it'll be 2.
  private static final Splitter FIELD_SPLITTER = Splitter.on(".").trimResults().omitEmptyStrings().limit(5);

  private FieldUtils() {
    // Do nothing
  }

  /**
   * Combine path segments into a single path.
   *
   * @param path
   * @return
   */
  public static String joinField(final List<String> path) {
    return FIELD_JOINER.join(path);
  }

  /**
   * Divider a path into segemnts.
   *
   * @param path
   * @return
   */
  public static List<String> fieldSplitter(final String field) {
    if (field == null) {
      return Collections.emptyList();
    }
    return FIELD_SPLITTER.splitToList(field);
  }

  /**
   * Combine path segments into a single path.
   *
   * @param path
   * @return
   */
  public static String joinField(final String... path) {
    return FIELD_JOINER.join(path);
  }

}
