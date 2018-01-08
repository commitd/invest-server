package io.committed.invest.test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static com.google.code.beanmatchers.BeanMatchers.isABeanWithValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class AbstractLombokTests {

  protected void testDataClasses(final Class<?>... classes) throws Exception {
    for (final Class<?> clazz : classes) {
      assertThat(clazz, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters(),
          hasValidBeanHashCode(), hasValidBeanEquals()));

      // Note we don't include hasValidBeanToString() since toString() whilst
      // can be overridden to be more usuful that the bean version
      testToString(clazz);

    }
  }

  protected void testImmutableInstances(final Object... instances) throws Exception {
    for (final Object o : instances) {
      assertThat(o, allOf(isABeanWithValidGettersAndSetters()));

      // Note we don't include hasValidBeanToString() since toString() whilst
      // can be overridden to be more usuful that the bean version
      testToString(o);
    }
  }

  protected void testImmutableDataClasses(final Class<?>... classes) throws Exception {
    for (final Class<?> clazz : classes) {
      assertThat(clazz,
          allOf(hasValidGettersAndSetters(), hasValidBeanHashCode(), hasValidBeanEquals()));

      // Note we don't include hasValidBeanToString() since toString() whilst
      // can be overridden to be more usuful that the bean version
      testToString(clazz);
    }
  }

  protected void testToString(final Class<?> clazz) throws Exception {
    testToString(clazz.getConstructor().newInstance());
  }

  protected void testToString(final Object i) {
    final String toString = i.toString();
    assert (toString != null);
    assert (toString.length() > 0);
  }

}
