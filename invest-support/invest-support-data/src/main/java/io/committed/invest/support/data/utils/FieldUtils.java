package io.committed.invest.support.data.utils;

import java.util.List;
import com.google.common.base.Joiner;

public class FieldUtils {
  private static final Joiner FIELD_JOINER = Joiner.on(".").skipNulls();


  public static String joinField(final List<String> path) {
    return FIELD_JOINER.join(path);
  }
}
