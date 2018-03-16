package io.committed.invest.test;

import java.time.Instant;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;
import org.meanbean.util.RandomValueGenerator;

public class BeanTestSupport {

  private static final long MAX_TIME = 2 * System.currentTimeMillis();
  private static final int MAX_ARRAY_SIZE = 10;

  private static final BeanTester beanTester = new BeanTester();

  static {
    final RandomValueGenerator rvg = getRandomValueGenerator();

    addFactory(Instant.class,
        () -> Instant.ofEpochMilli(Math.abs(rvg.nextLong()) % MAX_TIME));

    addFactory(String[].class, () -> {
      final int size = Math.abs(rvg.nextInt()) % MAX_ARRAY_SIZE;
      final String[] a = new String[size];

      for (int i = 0; i < size; i++) {
        a[i] = generateValue(String.class, Long.toString(rvg.nextLong()));
      }

      return a;
    });

  }

  private BeanTestSupport() {
    // Singleton
  }

  public static RandomValueGenerator getRandomValueGenerator() {
    return beanTester.getRandomValueGenerator();
  }

  public static <T> void addFactory(final Class<T> clazz, final Factory<T> factory) {
    beanTester.getFactoryCollection().addFactory(clazz, factory);
  }

  public static <T> T generateValue(final Class<T> clazz) {
    return (T) beanTester.getFactoryCollection().getFactory(clazz).create();
  }

  public static <T> T generateValue(final Class<T> clazz, final T defaultValue) {
    final T t = generateValue(clazz);
    return t != null ? t : defaultValue;
  }

  public static void test(final Class<?> clazz) {
    beanTester.testBean(clazz);
  }
}
